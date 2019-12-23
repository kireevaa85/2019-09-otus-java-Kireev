package ru.otus.veloorm.api.service;

public class DBServiceException extends RuntimeException {
    public DBServiceException(Throwable cause) {
        super(cause);
    }
}
