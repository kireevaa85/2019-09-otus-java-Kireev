package ru.otus.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.dao.UserDao;
import ru.otus.api.model.User;
import ru.otus.api.sessionmanager.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DBServiceUserImpl implements DBServiceUser {
    private static Logger logger = LoggerFactory.getLogger(DBServiceUserImpl.class);

    private final UserDao userDao;

    public DBServiceUserImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public long saveUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var userId = userDao.saveUser(user);
                sessionManager.commitSession();
                logger.info("created user: {}", userId);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DBServiceException(e);
            }
        }
    }

    @Override
    public Optional<User> getUser(long id) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var user = userDao.findById(id);
                logger.info("user: {}", user.isPresent() ? user.get().getId() : null);
                return user;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserFullInfo(long id) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var user = userDao.findById(id);
                logger.info("user: {}", user.isPresent() ? user.get().getId() : null);
                if (user.isPresent()) {
                    user.get().getAddress().getStreet();
                    user.get().getPhones().forEach(phoneDataSet -> phoneDataSet.getNumber());
                }
                return user;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAllUsersFullInfo() {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var users = userDao.findAll();
                logger.info("users size: {}", users != null ? users.size() : 0);
                if (users != null) {
                    users.forEach(user -> {user.getAddress().getStreet(); user.getPhones().forEach(phoneDataSet -> phoneDataSet.getNumber());});
                }
                return users;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return new ArrayList<>();
        }
    }

    @Override
    public void updateUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userDao.updateUser(user);
                sessionManager.commitSession();
                logger.info("updated user: {}", user.getId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DBServiceException(e);
            }
        }
    }

}
