package otus.java.project.dto.stock;

import otus.java.project.entity.Bar;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class BarDto {
    private TickerDto ticker;
    private Date date;
    private float open;
    private float high;
    private float low;
    private float close;
    private float adjustedClose;
    private int volume;
    private float dividendAmount;
    private float splitCoefficient;

    public BarDto(Bar bar) {
        this.ticker = new TickerDto(bar.getTicker());
        this.date = bar.getDate();
        this.open = bar.getOpen();
        this.high = bar.getHigh();
        this.low = bar.getLow();
        this.close = bar.getClose();
        this.adjustedClose = bar.getAdjustedClose();
        this.volume = bar.getVolume();
        this.dividendAmount = bar.getDividendAmount();
        this.splitCoefficient = bar.getSplitCoefficient();
    }
}