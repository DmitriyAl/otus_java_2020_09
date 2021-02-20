package ru.otus.messagesystem.message;

public enum MessageType {
    GET_USERS("GetUsers"),
    SAVE_USER("SaveUser");

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MessageType fromName(String name) {
        for (MessageType value : values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.format("No such message type '%s'", name));
    }
}
