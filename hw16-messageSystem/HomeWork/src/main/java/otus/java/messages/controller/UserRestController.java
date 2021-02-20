package otus.java.messages.controller;

import org.springframework.web.bind.annotation.*;
import otus.java.messages.dto.UserDto;
import otus.java.messages.model.User;
import otus.java.messages.service.UserService;

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