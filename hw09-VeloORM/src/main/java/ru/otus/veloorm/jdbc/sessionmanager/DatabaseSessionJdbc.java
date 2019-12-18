package ru.otus.veloorm.jdbc.sessionmanager;

import ru.otus.veloorm.api.sessionmanager.DatabaseSession;

import java.sql.Connection;

public class DatabaseSessionJdbc implements DatabaseSession {

    private final Connection connection;

    public DatabaseSessionJdbc(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
