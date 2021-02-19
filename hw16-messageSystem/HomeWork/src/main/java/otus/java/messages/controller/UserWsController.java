package otus.java.messages.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import otus.java.messages.dto.UserDto;
import otus.java.messages.service.FrontendService;

@Controller
public class UserWsController {
    private final FrontendService frontendService;
    private final SimpMessagingTemplate template;

    public UserWsController(FrontendService frontendService, SimpMessagingTemplate template) {
        this.frontendService = frontendService;
        this.template = template;
    }

    @MessageMapping("/users")
    public void getUsers() {
        frontendService.getUsers(data -> template.convertAndSend("/topic/users", data));
    }

    @MessageMapping("/user")
    public void addUser(UserDto user) {
        frontendService.saveUser(user, data -> template.convertAndSend("/topic/users", data));
    }
}