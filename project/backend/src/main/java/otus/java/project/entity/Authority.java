package otus.java.project.entity;

import otus.java.project.security.model.AuthorityType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Data
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private AuthorityType name;
}