package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.dao.UserDao;
import ru.otus.api.model.AddressDataSet;
import ru.otus.api.model.PhoneDataSet;
import ru.otus.api.model.User;
import ru.otus.api.service.DBServiceUser;
import ru.otus.api.service.DBServiceUserImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class DbServiceDemo {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                User.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DBServiceUserImpl(userDao);

        User initialUser = new User("dbServiceUser", 18, new AddressDataSet("street1"));
        initialUser.addPhone(new PhoneDataSet("phoneNumber1"));
        initialUser.addPhone(new PhoneDataSet("phoneNumber2"));
        initialUser.addPhone(new PhoneDataSet("phoneNumber3"));
        long id = dbServiceUser.saveUser(initialUser);
        Optional<User> user = dbServiceUser.getUserFullInfo(id);
        System.out.println(user);
        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );

        User userNEW = new User(id, "dbServiceUserNEW", 36, new AddressDataSet("streetNEW"));
        userNEW.addPhone(new PhoneDataSet("phoneNumber4NEW"));
        userNEW.addPhone(new PhoneDataSet("phoneNumber5NEW"));
        dbServiceUser.updateUser(userNEW);
        user = dbServiceUser.getUserFullInfo(id);
        user.ifPresentOrElse(
                crUser -> logger.info("UPDATED user:" + crUser),
                () -> logger.info("user was not updated")
        );
    }

}
