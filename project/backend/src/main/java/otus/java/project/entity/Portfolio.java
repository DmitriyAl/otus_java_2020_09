package otus.java.project.entity;

import otus.java.project.dto.portfolio.PortfolioDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "portfolios")
@Data
@NoArgsConstructor
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;
    private Date created;
    private Date updated;
    @OneToMany(mappedBy = "portfolio", orphanRemoval = true)
    private List<Order> orders;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Portfolio(PortfolioDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
    }
}
