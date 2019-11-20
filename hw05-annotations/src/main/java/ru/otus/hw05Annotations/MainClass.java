package ru.otus.hw05Annotations;

import ru.otus.hw05Annotations.myTestFramework.core.Runner;

import java.lang.reflect.InvocationTargetException;

public class MainClass {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String message = Runner.run("ru.otus.hw05Annotations.test.CalculatorTest");
        System.out.println(message);
    }

}
