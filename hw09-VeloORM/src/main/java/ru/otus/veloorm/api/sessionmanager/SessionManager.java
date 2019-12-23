package ru.otus.veloorm.api.sessionmanager;

public interface SessionManager extends AutoCloseable {

    void beginSession();

    void commitSession();

    void rollbackSession();

    @Override
    void close();

    DatabaseSession getCurrentSession();
}
