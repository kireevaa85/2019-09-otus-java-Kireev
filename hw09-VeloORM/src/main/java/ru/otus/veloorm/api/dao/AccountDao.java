package ru.otus.veloorm.api.dao;

import ru.otus.veloorm.api.model.Account;
import ru.otus.veloorm.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {

    Optional<Account> findById(long id);

    long saveAccount(Account account);

    void updateAccount(Account account);

    SessionManager getSessionManager();

}
