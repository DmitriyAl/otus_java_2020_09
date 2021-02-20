package otus.java.messages.service;

import otus.java.messages.dto.UserDto;
import otus.java.messages.dto.UsersDto;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;

public class FrontendServiceImpl implements FrontendService {
    public static final String SERVICE_NAME = "frontendService";
    private final MsClient msClient;

    public FrontendServiceImpl(MsClient msClient) {
        this.msClient = msClient;
    }

    @Override
    public void getUsers(MessageCallback<UsersDto> callback) {
        final Message message = msClient.produceMessage(UserService.SERVICE_NAME, null, MessageType.GET_USERS, callback);
        msClient.sendMessage(message);
    }

    @Override
    public void saveUser(UserDto user, MessageCallback<UsersDto> callback) {
        final Message message = msClient.produceMessage(UserService.SERVICE_NAME, user, MessageType.SAVE_USER, callback);
        msClient.sendMessage(message);
    }
}