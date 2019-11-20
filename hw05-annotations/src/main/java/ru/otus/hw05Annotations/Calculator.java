package ru.otus.hw05Annotations;

public class Calculator {
    private int x;
    private int y;

    public Calculator() {
    }

    public long sum() {
        return x + y;
    }

    public long diff() {
        return x - y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
