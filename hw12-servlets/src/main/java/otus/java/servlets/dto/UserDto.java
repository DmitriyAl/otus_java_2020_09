package otus.java.servlets.dto;

import otus.java.servlets.model.User;

public class UserDto {
    private long id;
    private String login;
    private String password;
    private String name;

    public UserDto() {
    }

    private UserDto(User dao) {
        this.id = dao.getId();
        this.login = dao.getLogin();
        this.password = dao.getPassword();
        this.name = dao.getName();
    }

    public static UserDto toDto(User dao) {
        return dao != null ? new UserDto(dao) : null;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
