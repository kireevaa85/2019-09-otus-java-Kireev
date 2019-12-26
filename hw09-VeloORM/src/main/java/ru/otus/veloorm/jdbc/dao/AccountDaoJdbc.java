package ru.otus.veloorm.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.veloorm.api.dao.AccountDao;
import ru.otus.veloorm.api.dao.AccountDaoException;
import ru.otus.veloorm.api.model.Account;
import ru.otus.veloorm.api.sessionmanager.SessionManager;
import ru.otus.veloorm.jdbc.sessionmanager.SessionManagerJdbc;
import ru.otus.veloorm.orm.JdbcTemplate;

import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {
    private static Logger logger = LoggerFactory.getLogger(AccountDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final JdbcTemplate jdbcTemplate;

    public AccountDaoJdbc(SessionManagerJdbc sessionManager, JdbcTemplate jdbcTemplate) {
        this.sessionManager = sessionManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Account> findById(long id) {
        try {
            var account = jdbcTemplate.load(id, Account.class);
            return Optional.ofNullable(account);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long saveAccount(Account account) {
        try {
            return jdbcTemplate.create(account);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AccountDaoException(e);
        }
    }

    @Override
    public void updateAccount(Account account) {
        try {
            jdbcTemplate.update(account);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AccountDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

}
