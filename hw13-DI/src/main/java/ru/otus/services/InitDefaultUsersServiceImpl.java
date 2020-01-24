package ru.otus.services;

import org.springframework.stereotype.Service;
import ru.otus.api.model.User;
import ru.otus.api.service.DBServiceUser;

@Service
public class InitDefaultUsersServiceImpl implements InitDefaultUsersService {

    private final DBServiceUser dbServiceUser;

    public InitDefaultUsersServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public void init() {
        User initialUser = new User("dbServiceUser1", 18);
        dbServiceUser.saveUser(initialUser);

        initialUser = new User("dbServiceUser2", 19);
        dbServiceUser.saveUser(initialUser);

        initialUser = new User("dbServiceUser3", 20);
        dbServiceUser.saveUser(initialUser);
    }
}
