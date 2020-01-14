package ru.otus;

import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.SessionFactory;
import ru.otus.api.dao.UserDao;
import ru.otus.api.model.AddressDataSet;
import ru.otus.api.model.PhoneDataSet;
import ru.otus.api.model.User;
import ru.otus.api.service.DBServiceUser;
import ru.otus.api.service.DBServiceUserImpl;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.server.SecurityType;
import ru.otus.server.UsersWebServer;
import ru.otus.server.UsersWebServerImpl;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class Main {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "AnyRealm";

    public static void main(String[] args) throws Exception {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                User.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DBServiceUserImpl(userDao);

        initUsers(dbServiceUser);

        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
        LoginService loginServiceForBasicSecurity = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);

        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
                SecurityType.BASIC,
                loginServiceForBasicSecurity,
                dbServiceUser,
                templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }

    private static void initUsers(DBServiceUser dbServiceUser) {
        User initialUser = new User("dbServiceUser1", 18);
        dbServiceUser.saveUser(initialUser);

        initialUser = new User("dbServiceUser2", 19);
        dbServiceUser.saveUser(initialUser);

        initialUser = new User("dbServiceUser3", 20);
        dbServiceUser.saveUser(initialUser);
    }

}
