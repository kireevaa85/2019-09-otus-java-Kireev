package ru.otus.hw05Annotations.test;

import ru.otus.hw05Annotations.Calculator;
import ru.otus.hw05Annotations.myTestFramework.annotations.*;

public class CalculatorTest {
    private static final int X = 7;
    private static final int Y = 7;

    private Calculator calculator = new Calculator();

    @BeforeAll
    public static void beforeAll() {
        System.out.println("DEBUG: Before all");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("DEBUG: After all");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("DEBUG: Before each");
        calculator.setX(X);
        calculator.setY(Y);
    }

    @AfterEach
    public void afterEach() {
        System.out.println("DEBUG: After each");
    }

    @Test
    public void testSum() throws Exception {
        long sum = calculator.sum();
        if (sum != X + Y) {
            throw new Exception("Calculator sum function destroed =(");
        }
    }

    @Test
    public void testSum4Fail() throws Exception {
        long sum = calculator.sum() + 1;
        if (sum != X + Y) {
            throw new Exception("Calculator sum function destroed =(");
        }
    }

    @Test
    public void testDiff() throws Exception {
        long diff = calculator.diff();
        if (diff != X - Y) {
            throw new Exception("Calculator diff function destroed =(");
        }
    }

    @Test
    public void testDiff4Fail() throws Exception {
        long diff = calculator.diff() - 1;
        if (diff != X - Y) {
            throw new Exception("Calculator diff function destroed =(");
        }
    }
}
