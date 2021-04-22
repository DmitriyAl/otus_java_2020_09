package otus.java.project.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Entity
@Table(name = "tickers")
@Data
@NoArgsConstructor
public class Ticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String information;
    @Column(unique = true)
    private String symbol;
    @Temporal(TemporalType.DATE)
    private Date lastRefreshed;
    private String outputSize;
    private TimeZone timeZone;
    @OneToMany(mappedBy = "ticker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Bar> bars = new ArrayList<>();
}
