package ru.otus.servlet;

import ru.otus.api.model.User;
import ru.otus.api.service.DBServiceUser;
import ru.otus.services.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class UserServlet extends HttpServlet {
    private static final String USER_PAGE_TEMPLATE = "user.html";
    private static final String INDEX_PAGE_TEMPLATE = "index.html";

    private final DBServiceUser dbServiceUser;
    private final TemplateProcessor templateProcessor;

    public UserServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser) {
        this.templateProcessor = templateProcessor;
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USER_PAGE_TEMPLATE, new HashMap<>()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));
        User newUser = new User(name, age);
        dbServiceUser.saveUser(newUser);

        resp.sendRedirect("index.html");
    }

}
