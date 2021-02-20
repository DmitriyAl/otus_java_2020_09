package otus.java.messages.handler;

import otus.java.messages.dto.UserDto;
import otus.java.messages.dto.UsersDto;
import otus.java.messages.model.User;
import otus.java.messages.service.DbServiceImpl;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;

import java.util.List;
import java.util.Optional;

public class GetUsersRequestHandler implements RequestHandler<UsersDto> {
    private final DbServiceImpl<UserDto, User, Long> userService;

    public GetUsersRequestHandler(DbServiceImpl<UserDto, User, Long> userService) {
        this.userService = userService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        final List<UserDto> all = userService.getAll();
        return Optional.of(MessageBuilder.buildReplyMessage(msg, new UsersDto(all)));
    }
}