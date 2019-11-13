package ru.otus.hw04dynamicProxy;

public class Demo {

    public static void main(String[] args) {
        new Demo().action();
    }

    public void action() {
        TestLogin testLogin = IoC.getTestLogin();
        testLogin.calculation(3);
        testLogin.calculation2(3);
        testLogin.calculation(6);
        testLogin.calculation2(6);
        testLogin.calculation(9);
        testLogin.calculation2(9);
    }
}



