package ru.otus.hw05Annotations.myTestFramework.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import static ru.otus.hw05Annotations.myTestFramework.util.ReflectionHelper.*;

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
                    doneCount = invokeCurrentTest(method, instance, result) ? doneCount + 1 : doneCount;
                } catch (Exception e) {
                    result.append(e.getMessage()).append("\n");
                } finally {
                    invokeMethods(map.get(AFTER_EACH), instance, result);
                }
            }
        } catch (Exception e) {
            result.append(e.getMessage()).append("\n");
        } finally {
            invokeMethods(map.get(AFTER_ALL), null, result);
        }

        result.insert(0, className + ": test count " + map.get(TEST).size() + ", done " + doneCount + ", fail " + (map.get(TEST).size() - doneCount) + "\n");
        return result.toString();
    }

    private static boolean invokeCurrentTest(Method method, Object instance, StringBuilder result) {
        try {
            method.invoke(instance);
            result.append("\t").append(method.getName()).append(" - done.\n");
            return true;
        } catch (InvocationTargetException e) {
            result.append("\t").append(method.getName()).append(" - fail. ").append(e.getTargetException().getMessage()).append("\n");
        } catch (IllegalAccessException e) {
            result.append("\t").append(method.getName()).append(" - fail. ").append(e.getMessage()).append("\n");
        }
        return false;
    }

}
