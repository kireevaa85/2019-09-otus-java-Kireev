package ru.otus.threads;

public class HWThreads {

    public static void main(String[] args) {
        new HWThreads().go();
    }

    public void go() {
        Printer printer = new Printer(1, this);
        Printer printer2 = new Printer(2, this);
        printer.start();
        printer2.start();
    }

}
