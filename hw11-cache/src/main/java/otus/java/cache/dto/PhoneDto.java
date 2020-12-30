package otus.java.cache.dto;

import otus.java.cache.model.Phone;

import java.util.UUID;

public class PhoneDto {
    private UUID id;
    private String number;
    private long userId;

    public PhoneDto() {
    }

    public PhoneDto(Phone dao) {
        this.id = dao.getId();
        this.number = dao.getNumber();
        this.userId = dao.getUser().getId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
