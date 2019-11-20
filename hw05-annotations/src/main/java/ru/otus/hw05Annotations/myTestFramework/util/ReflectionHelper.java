package ru.otus.hw05Annotations.myTestFramework.util;

import ru.otus.hw05Annotations.myTestFramework.annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static ru.otus.hw05Annotations.myTestFramework.core.Runner.*;

public final class ReflectionHelper {

    public static Map<String, Set<Method>> fillMethodsMap(Method[] methods) {
        Map<String, Set<Method>> result = new HashMap<>();
        result.put(BEFORE_ALL, new HashSet<>());
        result.put(AFTER_ALL, new HashSet<>());
        result.put(BEFORE_EACH, new HashSet<>());
        result.put(AFTER_EACH, new HashSet<>());
        result.put(TEST, new HashSet<>());
        for (Method method : methods) {
            if (method.getDeclaredAnnotation(BeforeAll.class) != null) {
                result.get(BEFORE_ALL).add(method);
            } else if (method.getDeclaredAnnotation(AfterAll.class) != null) {
                result.get(AFTER_ALL).add(method);
            } else if (method.getDeclaredAnnotation(BeforeEach.class) != null) {
                result.get(BEFORE_EACH).add(method);
            } else if (method.getDeclaredAnnotation(AfterEach.class) != null) {
                result.get(AFTER_EACH).add(method);
            } else if (method.getDeclaredAnnotation(Test.class) != null) {
                result.get(TEST).add(method);
            }
        }
        return result;
    }

    public static void invokeMethods(Set<Method> methods, Object instance) throws Exception {
        StringBuilder errMessage = new StringBuilder();
        for (Method method : methods) {
            try {
                method.invoke(instance);
            } catch (Exception e) {
                errMessage.append(e.getMessage()).append("\n");
            }
        }
        if (errMessage.length() > 0) {
            throw new Exception(errMessage.toString());
        }
    }

    public static void invokeMethods(Set<Method> methods, Object instance, StringBuilder result) {
        try {
            invokeMethods(methods, instance);
        } catch (Exception e) {
            result.append(e.getMessage()).append("\n");
        }
    }

}
