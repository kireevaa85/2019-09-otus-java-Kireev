package ru.otus.servlet;

import ru.otus.api.service.DBServiceUser;
import ru.otus.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class UsersServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "users.html";
    private static final String TEMPLATE_ATTR_USERS = "users";

    private final DBServiceUser dbServiceUser;
    private final TemplateProcessor templateProcessor;

    public UsersServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser) {
        this.templateProcessor = templateProcessor;
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        var allUsers = dbServiceUser.getAllUsersFullInfo();
        allUsers.forEach(user -> sb.append("<tr>\n            <td>")
                .append(user.getId())
                .append("</td>\n")
                .append("            <td>")
                .append(user.getName())
                .append("</td>\n")
                .append("            <td>")
                .append(user.getAge())
                .append("</td>\n")
                .append("            <td>")
                .append(user.getAddress().toString())
                .append("</td>\n")
                .append("            <td>")
                .append(user.getPhones().toString())
                .append("</td>\n")
                .append("        </tr>"));
        paramsMap.put(TEMPLATE_ATTR_USERS, sb.toString());

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

}
