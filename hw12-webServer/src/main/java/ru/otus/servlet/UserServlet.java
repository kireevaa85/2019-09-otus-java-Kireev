package ru.otus.servlet;

import ru.otus.api.service.DBServiceUser;
import ru.otus.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserServlet extends HttpServlet {
    private static final String USER_PAGE_TEMPLATE = "user.html";

    private final DBServiceUser dbServiceUser;
    private final TemplateProcessor templateProcessor;

    public UserServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser) {
        this.templateProcessor = templateProcessor;
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USER_PAGE_TEMPLATE, paramsMap));
    }
}
