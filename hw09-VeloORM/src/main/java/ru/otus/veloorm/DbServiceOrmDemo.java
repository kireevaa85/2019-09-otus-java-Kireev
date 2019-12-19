package ru.otus.veloorm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.veloorm.api.dao.UserDao;
import ru.otus.veloorm.api.model.User;
import ru.otus.veloorm.api.service.DBServiceUser;
import ru.otus.veloorm.api.service.DBServiceUserImpl;
import ru.otus.veloorm.h2.DataSourceH2;
import ru.otus.veloorm.jdbc.dao.UserDaoJdbc;
import ru.otus.veloorm.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class DbServiceOrmDemo {
    private static Logger logger = LoggerFactory.getLogger(DbServiceOrmDemo.class);

    public static void main(String[] args) throws SQLException {
        DataSource dataSource = new DataSourceH2();
        DbServiceOrmDemo dbServiceOrmDemo = new DbServiceOrmDemo();

        dbServiceOrmDemo.createTable(dataSource);

        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
        UserDao userDao = new UserDaoJdbc(sessionManager);
        DBServiceUser dbServiceUser = new DBServiceUserImpl(userDao);

        long id = dbServiceUser.saveUser(new User(0, "dbServiceUser", 18));
        Optional<User> user = dbServiceUser.getUser(id);
        System.out.println(user);
        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );

        dbServiceUser.updateUser(new User(0, "dbServiceUserNEW", 36));
        user = dbServiceUser.getUser(id);
        user.ifPresentOrElse(
                crUser -> logger.info("UPDATED user:" + crUser),
                () -> logger.info("user was not updated")
        );
    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table user(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }

}
