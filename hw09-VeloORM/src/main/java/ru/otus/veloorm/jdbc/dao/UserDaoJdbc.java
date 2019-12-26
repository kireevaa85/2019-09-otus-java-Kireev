package ru.otus.veloorm.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.veloorm.api.dao.UserDao;
import ru.otus.veloorm.api.dao.UserDaoException;
import ru.otus.veloorm.api.model.User;
import ru.otus.veloorm.api.sessionmanager.SessionManager;
import ru.otus.veloorm.jdbc.sessionmanager.SessionManagerJdbc;
import ru.otus.veloorm.orm.JdbcTemplate;

import java.util.Optional;

public class UserDaoJdbc implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final JdbcTemplate jdbcTemplate;

    public UserDaoJdbc(SessionManagerJdbc sessionManager, JdbcTemplate jdbcTemplate) {
        this.sessionManager = sessionManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findById(long id) {
        try {
            var user = jdbcTemplate.load(id, User.class);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long saveUser(User user) {
        try {
            return jdbcTemplate.create(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            jdbcTemplate.update(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

}
