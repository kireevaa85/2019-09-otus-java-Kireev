package ru.otus.hw06Bankomat.cassette;

public class InsufficientAmountCassetteException extends Exception {

    public InsufficientAmountCassetteException() {
    }

    public InsufficientAmountCassetteException(String message) {
        super(message);
    }
}
