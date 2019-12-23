package ru.otus.veloorm.orm;

public interface JdbcTemplate {

    <T> long create(T objectData);

    <T> void update(T objectData);

    <T> T load(long id, Class<T> clazz);

}
