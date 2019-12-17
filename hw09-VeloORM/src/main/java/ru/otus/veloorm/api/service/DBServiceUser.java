package ru.otus.veloorm.api.service;

import ru.otus.veloorm.api.model.User;

import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

}
