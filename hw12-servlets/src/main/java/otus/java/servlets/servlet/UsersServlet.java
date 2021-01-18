package otus.java.servlets.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import otus.java.servlets.dto.UserDto;
import otus.java.servlets.model.User;
import otus.java.servlets.service.DbService;
import otus.java.servlets.service.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UsersServlet extends HttpServlet {
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_NAME = "name";
    private static final String USERS_PAGE_TEMPLATE = "users.html";
    private static final String TEMPLATE_USERS = "users";

    private final DbService<User, Long> userService;
    private final TemplateProcessor templateProcessor;

    public UsersServlet(TemplateProcessor templateProcessor, DbService<User, Long> userService) {
        this.templateProcessor = templateProcessor;
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TEMPLATE_USERS, userService.getAll().stream().map(UserDto::toDto).collect(Collectors.toList()));
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter(PARAM_LOGIN);
        String password = req.getParameter(PARAM_PASSWORD);
        String name = req.getParameter(PARAM_NAME);
        userService.save(new User(login, password, name));
        resp.sendRedirect("/users");
    }
}