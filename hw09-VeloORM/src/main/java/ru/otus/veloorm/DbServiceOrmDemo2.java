package ru.otus.veloorm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.veloorm.api.dao.AccountDao;
import ru.otus.veloorm.api.model.Account;
import ru.otus.veloorm.api.service.DBServiceAccount;
import ru.otus.veloorm.api.service.DBServiceAccountImpl;
import ru.otus.veloorm.h2.DataSourceH2;
import ru.otus.veloorm.jdbc.DbExecutor;
import ru.otus.veloorm.jdbc.dao.AccountDaoJdbc;
import ru.otus.veloorm.jdbc.sessionmanager.SessionManagerJdbc;
import ru.otus.veloorm.orm.EntityMetadataHolder;
import ru.otus.veloorm.orm.JdbcTemplateImpl;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class DbServiceOrmDemo2 {
    private static Logger logger = LoggerFactory.getLogger(DbServiceOrmDemo2.class);

    public static void main(String[] args) throws SQLException {
        DataSource dataSource = new DataSourceH2();
        DbServiceOrmDemo2 dbServiceOrmDemo2 = new DbServiceOrmDemo2();

        dbServiceOrmDemo2.createTable(dataSource);

        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
        AccountDao accountDao = new AccountDaoJdbc(sessionManager, new JdbcTemplateImpl(sessionManager, new DbExecutor(), new EntityMetadataHolder()));
        DBServiceAccount dbServiceAccount = new DBServiceAccountImpl(accountDao);

        long id = dbServiceAccount.saveAccount(new Account("dbServiceAccount", new BigDecimal(1800)));
        Optional<Account> account = dbServiceAccount.getAccount(id);
        System.out.println(account);
        account.ifPresentOrElse(
                crAccount -> logger.info("created account, type:{}", crAccount.getType()),
                () -> logger.info("account was not created")
        );

        dbServiceAccount.updateAccount(new Account(id, "dbServiceAccountNEW", new BigDecimal(3600)));
        account = dbServiceAccount.getAccount(id);
        account.ifPresentOrElse(
                crAccount -> logger.info("UPDATED account:" + crAccount),
                () -> logger.info("account was not updated")
        );
    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }
}
