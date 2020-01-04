package ru.otus.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.dao.UserDao;
import ru.otus.api.model.User;
import ru.otus.api.sessionmanager.SessionManager;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;

import java.util.Optional;

public class DBServiceUserImpl implements DBServiceUser {
    private static Logger logger = LoggerFactory.getLogger(DBServiceUserImpl.class);

    HwCache<Long, User> cache = new MyCache<>();
    HwCache<Long, User> fullCache = new MyCache<>();

    private final UserDao userDao;

    public DBServiceUserImpl(UserDao userDao) {
        this.userDao = userDao;
        HwListener<Long, User> listener =
                (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);
        cache.addListener(listener);
        fullCache.addListener(listener);
    }

    @Override
    public long saveUser(User user) {
        long userId = -1;
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userId = userDao.saveUser(user);
                sessionManager.commitSession();
                logger.info("created user: {}", userId);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DBServiceException(e);
            }
        }
        cache.remove(userId);
        fullCache.remove(userId);
        return userId;
    }

    @Override
    public Optional<User> getUser(long id) {
        if (cache.get(id) != null) {
            return Optional.of(cache.get(id));
        }
        Optional<User> user = Optional.empty();
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                user = userDao.findById(id);
                logger.info("user: {}", user.isPresent() ? user.get().getId() : null);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
        }
        user.ifPresentOrElse(value -> cache.put(id, value), () -> cache.remove(id));
        return user;
    }

    @Override
    public Optional<User> getUserFullInfo(long id) {
        if (fullCache.get(id) != null) {
            return Optional.of(fullCache.get(id));
        }
        Optional<User> user = Optional.empty();
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                user = userDao.findById(id);
                logger.info("user: {}", user.isPresent() ? user.get().getId() : null);
                if (user.isPresent()) {
                    user.get().getAddress().getStreet();
                    user.get().getPhones().forEach(phoneDataSet -> phoneDataSet.getNumber());
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
        }
        user.ifPresentOrElse(value -> fullCache.put(id, value), () -> fullCache.remove(id));
        return user;
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
        cache.remove(user.getId());
        fullCache.remove(user.getId());
    }

}
