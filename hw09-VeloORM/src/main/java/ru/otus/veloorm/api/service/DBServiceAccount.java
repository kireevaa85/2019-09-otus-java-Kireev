package ru.otus.veloorm.api.service;

import ru.otus.veloorm.api.model.Account;

import java.util.Optional;

public interface DBServiceAccount {

    long saveAccount(Account account);

    Optional<Account> getAccount(long id);

    void updateAccount(Account account);

}
