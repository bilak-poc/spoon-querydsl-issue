package com.github.bilakpoc.spoon;

import java.lang.annotation.Annotation;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import spoon.MavenLauncher;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.CtScanner;
import spoon.reflect.visitor.filter.TypeFilter;

/**
 * @author Lukáš Vasek
 */
public class CallHierarchyExporter {


    @Test
    public void testCallHierarchy() {

        final Path modulePath = Paths.get(System.getProperty("user.dir"));
        final MavenLauncher launcher = new MavenLauncher(modulePath.getParent().toString(), MavenLauncher.SOURCE_TYPE.APP_SOURCE);
        launcher.getEnvironment().setNoClasspath(true);
        final CtModel model = launcher.buildModel();

        final Factory factory = launcher.getFactory();

        final CtClass<?> targetClass = factory.Class().get("com.github.bilakpoc.spoon.integration.AbstractIntegrationCaller");
        final CtMethod<?> targetMethod = targetClass.getMethodsByName("callExternalService").get(0);

        final List<CtMethod> callers = model.getElements(new TypeFilter<CtInvocation>(CtInvocation.class) {
            @Override
            public boolean matches(CtInvocation element) {
                final CtExecutableReference executable = element.getExecutable();
                if (executable.getSimpleName().equals(targetMethod.getSimpleName()) && executable.isOverriding(targetMethod.getReference())) {
                    return true;
                }
                return false;
            }
        }).stream().map(i -> {
            final CtMethod parent = i.getParent(CtMethod.class);
            parent.putMetadata("ExternalService", i.getArguments().get(0).toString());
            return parent;
        }).collect(Collectors.toList());

        final CtTypeReference<? extends Annotation> requestAnnotation =
                factory.Type().createReference("org.springframework.web.bind.annotation.RequestMapping");

        model.getRootPackage().accept(new CtScanner() {
            @Override
            public <T> void visitCtInvocation(CtInvocation<T> invocation) {
                final CtExecutableReference<T> executable = invocation.getExecutable();
                for (int i = 0; i < callers.size(); i++) {
                    final CtMethod method = callers.get(i);
                    if (method.getSignature().equals(executable.getSignature())) {
                        final CtMethod parent = invocation.getParent(CtMethod.class);
                        final CtAnnotation<? extends Annotation> annotation = parent.getAnnotation(requestAnnotation);
                        if (annotation != null) {
                            System.out.println(method.getMetadata("ExternalService") + " -> " + annotation.getValue("value").toString());
                        }
                    }
                }
                super.visitCtInvocation(invocation);
            }
        });
    }
}




