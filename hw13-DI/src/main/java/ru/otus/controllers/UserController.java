package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.api.model.User;
import ru.otus.api.service.DBServiceUser;
import ru.otus.services.InitDefaultUsersService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private static final String INDEX_PAGE_TEMPLATE = "index.html";
    private static final String USER_PAGE_TEMPLATE = "user.html";
    private static final String USERS_PAGE_TEMPLATE = "users.html";
    private static final String TEMPLATE_ATTR_USERS = "users";

    private final DBServiceUser dbServiceUser;

    public UserController(InitDefaultUsersService initDefaultUsersService, DBServiceUser dbServiceUser) {
        initDefaultUsersService.init();;
        this.dbServiceUser = dbServiceUser;
    }

    @GetMapping("/")
    public String  startPageView(Model model) {
        return INDEX_PAGE_TEMPLATE;
    }

    @GetMapping("/users")
    public String userListView(Model model) {
        List<User> users = dbServiceUser.getAllUsers();
        model.addAttribute(TEMPLATE_ATTR_USERS, users != null ? users : new ArrayList<>());
        return USERS_PAGE_TEMPLATE;
    }

    @GetMapping("/user")
    public String userCreateView(Model model) {
        model.addAttribute("user", new User());
        return USER_PAGE_TEMPLATE;
    }

    @PostMapping("/user")
    public RedirectView userSave(@ModelAttribute User user) {
        dbServiceUser.saveUser(user);
        return new RedirectView("/", true);
    }

}
