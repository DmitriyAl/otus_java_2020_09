package otus.java.messages.handler;

import otus.java.messages.dto.UserDto;
import otus.java.messages.dto.UsersDto;
import otus.java.messages.model.User;
import otus.java.messages.service.DbServiceImpl;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;

import java.util.List;
import java.util.Optional;

public class SaveUserRequestHandler implements RequestHandler<UserDto> {
    private final DbServiceImpl<UserDto, User, Long> userService;

    public SaveUserRequestHandler(DbServiceImpl<UserDto, User, Long> userService) {
        this.userService = userService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        final UserDto payload = MessageHelper.getPayload(msg);
        userService.save(new User(payload.getName()));
        final List<UserDto> all = userService.getAll();
        return Optional.of(MessageBuilder.buildReplyMessage(msg, new UsersDto(all)));
    }
}