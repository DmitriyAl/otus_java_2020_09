package otus.java.messages.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import otus.java.messages.dto.UserDto;
import otus.java.messages.model.User;
import otus.java.messages.service.UserService;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/user/list"})
    public String clientsListView(Model model) {
        List<UserDto> users = userService.getAll();
        model.addAttribute("users", users);
        return "usersList";
    }

    @GetMapping("/user/create")
    public String clientCreateView(Model model) {
        model.addAttribute("user", new UserDto());
        return "userCreate";
    }

    @PostMapping("/user/save")
    public RedirectView clientSave(@ModelAttribute User user) {
        userService.save(user);
        return new RedirectView("/", true);
    }
}