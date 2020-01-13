package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
                SecurityType.BASIC,
                loginServiceForBasicSecurity,
                dbServiceUser,
                gson,
                templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }

    private static void initUsers(DBServiceUser dbServiceUser) {
        User initialUser = new User("dbServiceUser1", 18, new AddressDataSet("street1"));
        initialUser.addPhone(new PhoneDataSet("phoneNumber1"));
        initialUser.addPhone(new PhoneDataSet("phoneNumber2"));
        initialUser.addPhone(new PhoneDataSet("phoneNumber3"));
        dbServiceUser.saveUser(initialUser);

        initialUser = new User("dbServiceUser2", 19, new AddressDataSet("street2"));
        initialUser.addPhone(new PhoneDataSet("phoneNumber4"));
        initialUser.addPhone(new PhoneDataSet("phoneNumber5"));
        initialUser.addPhone(new PhoneDataSet("phoneNumber6"));
        dbServiceUser.saveUser(initialUser);

        initialUser = new User("dbServiceUser3", 20, new AddressDataSet("street3"));
        initialUser.addPhone(new PhoneDataSet("phoneNumber7"));
        initialUser.addPhone(new PhoneDataSet("phoneNumber8"));
        initialUser.addPhone(new PhoneDataSet("phoneNumber9"));
        dbServiceUser.saveUser(initialUser);
    }

}
