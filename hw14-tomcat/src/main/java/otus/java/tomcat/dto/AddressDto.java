package otus.java.tomcat.dto;

import otus.java.tomcat.model.Address;

public class AddressDto {
    private Long id;
    private String street;
    private long userId;

    public AddressDto() {
    }

    public AddressDto(Address dao) {
        this.id = dao.getId();
        this.street = dao.getStreet();
        this.userId = dao.getUser().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
