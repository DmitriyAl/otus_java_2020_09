package otus.java.messages.dto;

import otus.java.messages.model.Phone;
import otus.java.messages.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {
    private long id;
    private String name;
    private AddressDto address;
    private List<PhoneDto> phones;

    public UserDto() {
    }

    public UserDto(User dao) {
        this.id = dao.getId();
        this.name = dao.getName();
        this.address = dao.getAddress() == null ? null : dao.getAddress().toDto();
        this.phones = dao.getPhones() == null ? null : dao.getPhones().stream().map(Phone::toDto).collect(Collectors.toList());
    }

    public static UserDto toDto(User dao) {
        return new UserDto(dao);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public List<PhoneDto> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDto> phones) {
        this.phones = phones;
    }
}
