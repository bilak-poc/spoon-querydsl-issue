package com.github.bilakpoc.spoon;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.bilakpoc.spoon.integration.ExternalService;
import spoon.MavenLauncher;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtExecutableReference;
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
            parent.putMetadata("ExternalService", getServiceNameAndVersion(i.getArguments().get(0).toString()));
            return parent;
        }).collect(Collectors.toList());


        model.getRootPackage().accept(new CtScanner() {
            @Override
            public <T> void visitCtInvocation(CtInvocation<T> invocation) {
                final CtExecutableReference<T> executable = invocation.getExecutable();
                for (int i = 0; i < callers.size(); i++) {
                    final CtMethod method = callers.get(i);
                    if (method.getSignature().equals(executable.getSignature())) {
                        final CtMethod parent = invocation.getParent(CtMethod.class);
                        final Method originalMethod = parent.getReference().getActualMethod();
                        System.out.println(method.getMetadata("ExternalService") + " -> " + getMethodMapping(originalMethod));
                    }
                }
                super.visitCtInvocation(invocation);
            }
        });
    }

    private String getServiceNameAndVersion(final String enumPath) {
        try {
            final Class clazz = Class.forName(enumPath.substring(0, enumPath.lastIndexOf(".")));
            final EnumSet<?> enumSet = EnumSet.allOf(clazz);
            final String enumName = enumPath.substring(enumPath.lastIndexOf(".") + 1);
            final ExternalService externalService = getExternalServiceByName(enumSet, enumName);
            return externalService.getNameWithVersion();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to get service for " + enumPath);
        }
    }

    private ExternalService getExternalServiceByName(final EnumSet<?> enumSet, final String serviceName) {
        return StreamSupport.stream(enumSet.spliterator(), false)
                .filter(e -> ((Enum) e).name().equals(serviceName))
                .map(e -> (ExternalService) e)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unable to find service " + serviceName));

    }

    private List<String> getMethodMapping(final Method method) {
        final Optional<String> restControllerPath = getRestControllerPath(method.getDeclaringClass());
        final List<String> methodMappings = new LinkedList<>();
        final RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
        if (requestMapping != null && !StringUtils.isEmpty(requestMapping.value())) {
            final String requestType = getRequestType(requestMapping);
            Arrays.asList(requestMapping.value()).forEach(rm -> methodMappings.add(rm.concat(" ").concat(requestType)));
        }

        return methodMappings
                .stream()
                .map(m -> restControllerPath.map(rcp -> rcp.concat(m)).orElse(m))
                .collect(Collectors.toList());
    }

    private String getRequestType(final RequestMapping requestMapping) {
        return requestMapping.method() != null && requestMapping.method().length > 0 ?
                Arrays.stream(requestMapping.method())
                        .map(m -> "[".concat(m.name()).concat("]"))
                        .collect(Collectors.joining(" ")) :
                "[GET]";
    }

    private Optional<String> getRestControllerPath(final Class<?> declaringClass) {
        final RestController restController = AnnotationUtils.findAnnotation(declaringClass, RestController.class);
        Optional<String> restControllerPath = Optional.empty();
        if (restController != null && !StringUtils.isEmpty(restController.value())) {
            restControllerPath = Optional.of(restController.value());
        }

        return restControllerPath;
    }
}




