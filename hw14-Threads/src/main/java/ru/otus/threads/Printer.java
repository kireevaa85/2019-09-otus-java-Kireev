package ru.otus.threads;

public class Printer extends Thread {
    private final Object monitor;

    private final int id;
    private int number = 0;
    private int delta = 1;

    public Printer(int id, Object monitor) {
        this.id = id;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        synchronized (monitor) {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("\t".repeat(id) + nextNumber());
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                monitor.notify();
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private int nextNumber() {
        if (number == 1) {
            delta = 1;
        } else if (number == 10) {
            delta = -1;
        }
        number += delta;
        return number;
    }

}
