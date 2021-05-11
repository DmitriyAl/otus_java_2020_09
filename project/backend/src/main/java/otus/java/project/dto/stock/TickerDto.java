package otus.java.project.dto.stock;

import otus.java.project.entity.Ticker;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.TimeZone;

@Data
@NoArgsConstructor
public class TickerDto {
    private long id;
    private String information;
    private String symbol;
    private Date lastRefreshed;
    private String outputSize;
    private TimeZone timeZone;

    public TickerDto(Ticker dao) {
        this.id = dao.getId();
        this.information = dao.getInformation();
        this.symbol = dao.getSymbol();
        this.lastRefreshed = dao.getLastRefreshed();
        this.outputSize = dao.getOutputSize();
        this.timeZone = dao.getTimeZone();
    }
}
