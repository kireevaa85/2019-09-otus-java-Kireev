package ru.otus.veloorm.api.dao;

import ru.otus.veloorm.api.model.User;
import ru.otus.veloorm.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);

    long saveUser(User user);

    SessionManager getSessionManager();
}
