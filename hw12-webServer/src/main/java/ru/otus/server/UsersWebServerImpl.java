package ru.otus.server;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
import ru.otus.api.service.DBServiceUser;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.services.TemplateProcessor;
import ru.otus.servlet.UserServlet;
import ru.otus.servlet.UsersServlet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class UsersWebServerImpl implements UsersWebServer {
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";
    private static final String ROLE_NAME_ADMIN = "admin";
    private static final String CONSTRAINT_NAME = "auth";

    private final int port;
    private final SecurityType securityType;
    private final LoginService loginServiceForBasicSecurity;
    private final DBServiceUser dbServiceUser;
    private final TemplateProcessor templateProcessor;
    private final Server server;

    public UsersWebServerImpl(int port, SecurityType securityType,
                              LoginService loginServiceForBasicSecurity,
                              DBServiceUser dbServiceUser,
                              TemplateProcessor templateProcessor) {
        this.port = port;
        this.securityType = securityType;
        this.loginServiceForBasicSecurity = loginServiceForBasicSecurity;
        this.dbServiceUser = dbServiceUser;
        this.templateProcessor = templateProcessor;
        server = initContext();
    }

    @Override
    public void start() throws Exception {
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    private Server initContext() {
        HandlerList handlers = new HandlerList();

        ResourceHandler resourceHandler = createResourceHandler();
        handlers.addHandler(resourceHandler);

        ServletContextHandler servletContextHandler = createServletContextHandler();
        handlers.addHandler(applySecurity(servletContextHandler));

        Server srv = new Server(port);
        srv.setHandler(handlers);
        return srv;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new UserServlet(templateProcessor, dbServiceUser)), "/user");
        servletContextHandler.addServlet(new ServletHolder(new UsersServlet(templateProcessor, dbServiceUser)), "/users");
        return servletContextHandler;
    }

    private Handler applySecurity(ServletContextHandler servletContextHandler) {
        if (securityType == SecurityType.NONE) {
            return servletContextHandler;
        } else if (securityType == SecurityType.BASIC) {
            return createBasicAuthSecurityHandler(servletContextHandler, "/users", "/user");
        } else {
            throw new InvalidSecurityTypeException(securityType);
        }
    }

    private SecurityHandler createBasicAuthSecurityHandler(ServletContextHandler context, String... paths) {
        Constraint constraint = new Constraint();
        constraint.setName(CONSTRAINT_NAME);
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[]{ROLE_NAME_ADMIN});

        List<ConstraintMapping> constraintMappings = new ArrayList<>();
        IntStream.range(0, paths.length).forEachOrdered(i -> {
            ConstraintMapping mapping = new ConstraintMapping();
            mapping.setPathSpec(paths[i]);
            mapping.setConstraint(constraint);
            constraintMappings.add(mapping);
        });

        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        //как декодировать стороку с юзером:паролем https://www.base64decode.org/
        security.setAuthenticator(new BasicAuthenticator());

        security.setLoginService(loginServiceForBasicSecurity);
        security.setConstraintMappings(constraintMappings);
        security.setHandler(new HandlerList(context));

        return security;
    }

}
