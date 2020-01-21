package ru.otus;

//import org.eclipse.jetty.security.HashLoginService;
//import org.eclipse.jetty.security.LoginService;
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
//import ru.otus.server.UsersWebServer;
//import ru.otus.server.UsersWebServerImpl;
//import ru.otus.services.TemplateProcessor;
//import ru.otus.services.TemplateProcessorImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница создания пользователя
    http://localhost:8080/user

    // Страница пользователей
    http://localhost:8080/users

*/
public class Main {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "AnyRealm";

    public static void main(String[] args) throws Exception {
//        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
//        LoginService loginServiceForBasicSecurity = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);
//
//        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
//
//        UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
//                loginServiceForBasicSecurity,
//                dbServiceUser,
//                templateProcessor);
//
//        usersWebServer.start();
//        usersWebServer.join();
    }

}
