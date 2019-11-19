package ru.otus.hw05Annotations.myTestFramework.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import static ru.otus.hw05Annotations.myTestFramework.util.ReflectionHelper.fillMethodsMap;
import static ru.otus.hw05Annotations.myTestFramework.util.ReflectionHelper.invokeMethods;

public class Runner {

    public static final String BEFORE_ALL = "BEFORE_ALL";
    public static final String AFTER_ALL = "AFTER_ALL";
    public static final String BEFORE_EACH = "BEFORE_EACH";
    public static final String AFTER_EACH = "AFTER_EACH";
    public static final String TEST = "TEST";

    public static String run(String className) throws ClassNotFoundException, NoSuchMethodException {
        StringBuilder result = new StringBuilder();
        Class<?> classTest = Class.forName(className);
        Constructor<?> defConstructor = classTest.getConstructor();
        Method[] methods = classTest.getDeclaredMethods();

        Map<String, Set<Method>> map = fillMethodsMap(methods);

        int doneCount = 0;
        try {
            invokeMethods(map.get(BEFORE_ALL), null);
            Object instance;
            for (Method method : map.get(TEST)) {
                instance = defConstructor.newInstance();
                try {
                    invokeMethods(map.get(BEFORE_EACH), instance);
                    try {
                        method.invoke(instance);
                        result.append("\t").append(method.getName()).append(" - done.\n");
                        doneCount++;
                    } catch (InvocationTargetException e) {
                        result.append("\t").append(method.getName()).append(" - fail. ").append(e.getTargetException().getMessage()).append("\n");
                    }
                } catch (Exception e) {
                    result.append(e.getMessage()).append("\n");
                } finally {
                    try {
                        invokeMethods(map.get(AFTER_EACH), instance);
                    } catch (Exception e) {
                        result.append(e.getMessage()).append("\n");
                    }
                }
            }
        } catch (Exception e) {
            result.append(e.getMessage()).append("\n");
        } finally {
            try {
                invokeMethods(map.get(AFTER_ALL), null);
            } catch (Exception e) {
                result.append(e.getMessage()).append("\n");
            }
        }

        result.insert(0, className + ": test count " + map.get(TEST).size() + ", done " + doneCount + ", fail " + (map.get(TEST).size() - doneCount) + "\n");
        return result.toString();
    }

}
