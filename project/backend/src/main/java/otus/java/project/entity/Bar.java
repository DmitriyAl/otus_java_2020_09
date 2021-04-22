package otus.java.project.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bars")
@Data
@NoArgsConstructor
public class Bar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private float open;
    private float high;
    private float low;
    private float close;
    private float adjustedClose;
    private int volume;
    private float dividendAmount;
    private float splitCoefficient;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticker_id")
    private Ticker ticker;
}
