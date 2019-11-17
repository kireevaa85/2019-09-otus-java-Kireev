package ru.otus.hw05Annotations.myTestFramework.core;

import ru.otus.hw05Annotations.myTestFramework.annotations.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class Runner {

    public static String run(String className) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        StringBuilder result = new StringBuilder();
        Class<?> classTest = Class.forName(className);
        Constructor<?> defConstructor = classTest.getConstructor();
        Method[] methods = classTest.getDeclaredMethods();

        Set<Method> beforeAllSet = new HashSet<>();
        Set<Method> afterAllSet = new HashSet<>();
        Set<Method> beforeEachSet = new HashSet<>();
        Set<Method> afterEachSet = new HashSet<>();
        Set<Method> testSet = new HashSet<>();

        for (Method method : methods) {
            if (method.getDeclaredAnnotation(BeforeAll.class) != null) {
                beforeAllSet.add(method);
            } else if (method.getDeclaredAnnotation(AfterAll.class) != null) {
                afterAllSet.add(method);
            } else if (method.getDeclaredAnnotation(BeforeEach.class) != null) {
                beforeEachSet.add(method);
            } else if (method.getDeclaredAnnotation(AfterEach.class) != null) {
                afterEachSet.add(method);
            } else if (method.getDeclaredAnnotation(Test.class) != null) {
                testSet.add(method);
            }
        }

        validateSingleAnnotation(beforeAllSet.size(), BeforeAll.class.getSimpleName(), result);
        validateSingleAnnotation(afterAllSet.size(), AfterAll.class.getSimpleName(), result);
        validateSingleAnnotation(beforeEachSet.size(), BeforeEach.class.getSimpleName(), result);
        validateSingleAnnotation(afterEachSet.size(), AfterEach.class.getSimpleName(), result);

        Object instance = defConstructor.newInstance();
        int doneCount = 0;
        int failCount = 0;
        invokeMethods(beforeAllSet, instance);
        for (Method method : testSet) {
            invokeMethods(beforeEachSet, instance);
            try {
                method.invoke(instance);
                result.append("\t").append(method.getName()).append(" - done.\n");
                doneCount++;
            } catch (InvocationTargetException e) {
                result.append("\t").append(method.getName()).append(" - fail. ").append(e.getTargetException().getMessage()).append("\n");
                failCount++;
            }
            invokeMethods(afterEachSet, instance);
        }
        invokeMethods(afterAllSet, instance);
        result.insert(0, className + ": test count " + testSet.size() + ", done " + doneCount + ", fail " + failCount + "\n");
        return result.toString();
    }

    private static void validateSingleAnnotation(int size, String simpleName, StringBuilder result) {
        if (size > 1) {
            result.append("Annotation ").append(simpleName).append(" should be in a single copy!\n");
        }
    }

    private static void invokeMethods(Set<Method> eachSet, Object instance) throws InvocationTargetException, IllegalAccessException {
        for (Method method : eachSet) {
            method.invoke(instance);
        }
    }

}
