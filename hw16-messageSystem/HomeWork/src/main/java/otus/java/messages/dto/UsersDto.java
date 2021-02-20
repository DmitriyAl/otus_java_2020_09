package otus.java.messages.dto;

import ru.otus.messagesystem.client.ResultDataType;

import java.util.List;

public class UsersDto extends ResultDataType {
    private List<UserDto> users;

    public UsersDto(List<UserDto> users) {
        this.users = users;
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}