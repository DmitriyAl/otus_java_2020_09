package otus.java.messages.service;

import otus.java.messages.dto.UserDto;
import otus.java.messages.dto.UsersDto;
import ru.otus.messagesystem.client.MessageCallback;

public interface FrontendService {
    void getUsers(MessageCallback<UsersDto> callback);

    void saveUser(UserDto user, MessageCallback<UsersDto> callback);
}