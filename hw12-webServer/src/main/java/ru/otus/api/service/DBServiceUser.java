package ru.otus.api.service;

import ru.otus.api.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

    Optional<User> getUserFullInfo(long id);

    List<User> getAllUsers();

    void updateUser(User user);

}
