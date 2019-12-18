package ru.otus.veloorm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.veloorm.api.dao.UserDao;
import ru.otus.veloorm.api.model.User;
import ru.otus.veloorm.api.service.DBServiceUser;
import ru.otus.veloorm.api.service.DBServiceUserImpl;
import ru.otus.veloorm.h2.DataSourceH2;
import ru.otus.veloorm.jdbc.DbExecutor;
import ru.otus.veloorm.jdbc.dao.UserDaoJdbc;
import ru.otus.veloorm.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class DbServiceDemo {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {
        DataSource dataSource = new DataSourceH2();
        DbServiceDemo demo = new DbServiceDemo();

        demo.createTable(dataSource);

        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutor<User> dbExecutor = new DbExecutor<>();
        UserDao userDao = new UserDaoJdbc(sessionManager, dbExecutor);
        DBServiceUser dbServiceUser = new DBServiceUserImpl(userDao);
        long id = dbServiceUser.saveUser(new User(0, "dbServiceUser"));
        Optional<User> user = dbServiceUser.getUser(id);

        System.out.println(user);
        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );

    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(50))")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }
}
