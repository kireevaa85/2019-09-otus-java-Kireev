package ru.otus.hw04dynamicProxy;

public class Demo {

    public static void main(String[] args) {
        new Demo().action();
    }

    public void action() {
        TestLogin testLogin = IoC.getTestLogin();
        testLogin.calculation(6);
    }
}



