package otus.java.jpql.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "phones")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "number")
    private String number;
    @ManyToOne
    private User user;

    public Phone() {
    }

    public Phone(String number, User user) {
        this.number = number;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

        Phone phone = (Phone) object;

        return Objects.equals(number, phone.number);
    }

    @Override
    public int hashCode() {
        return number != null ? number.hashCode() : 0;
    }
}