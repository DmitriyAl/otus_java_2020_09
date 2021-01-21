package otus.java.tomcat.model;

import otus.java.tomcat.dto.AddressDto;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "addresses")
public class Address implements IntegerId<Long>, HasDto<AddressDto> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "street")
    private String street;
    @OneToOne(mappedBy = "address", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User user;

    public Address() {
    }

    public Address(String street, User user) {
        this.street = street;
        this.user = user;
    }

    public AddressDto toDto() {
        return new AddressDto(this);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Address address = (Address) object;
        return Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return street != null ? street.hashCode() : 0;
    }
}