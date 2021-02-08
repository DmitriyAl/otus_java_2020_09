package otus.java.tomcat.controller;

import org.springframework.web.bind.annotation.*;
import otus.java.tomcat.dto.UserDto;
import otus.java.tomcat.model.User;
import otus.java.tomcat.service.UserService;

@RestController
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/user/{id}")
    public UserDto getClientById(@PathVariable(name = "id") long id) {
        return userService.getById(id);
    }

    @PostMapping("/api/user")
    public UserDto saveClient(@RequestBody User user) {
        return userService.getById(userService.save(user));
    }

    @GetMapping("/api/user/random")
    public UserDto findRandomClient() {
        return userService.getRandom();
    }
}