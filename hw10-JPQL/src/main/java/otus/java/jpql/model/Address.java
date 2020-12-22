package otus.java.jpql.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "addresses")
public class Address implements IntegerId<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "street")
    private String street;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    private User user;

    public Address() {
    }

    public Address(String street, User user) {
        this.street = street;
        this.user = user;
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