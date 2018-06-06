package com.github.bilakpoc.spoon;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import spoon.MavenLauncher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

/**
 * @author Lukáš Vasek
 */
public class CallHierarchyExporter {


    @Test
    public void testCallHierarchy(){

        final Path modulePath = Paths.get(System.getProperty("user.dir"));
        final MavenLauncher launcher = new MavenLauncher(modulePath.getParent().toString(), MavenLauncher.SOURCE_TYPE.APP_SOURCE);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.buildModel();

        // TODO if I know the class and the method can I create it directly and then search for all calls?

        final CtModel model = launcher.getModel();
        final List<CtClass<?>> classes =  model.getElements(new TypeFilter<>(CtClass.class));
        for (final CtClass<?> clazz : classes){

        }

    }
}
