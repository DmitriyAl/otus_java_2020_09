package otus.java.project.dto.stock;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import otus.java.project.entity.Ticker;
import otus.java.project.json.StockDataDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@JsonDeserialize(using = StockDataDeserializer.class)
public class StockDataDto {
    private TickerDto metadata;
    private List<BarDto> bars;

    public StockDataDto(Ticker ticker) {
        this.metadata = new TickerDto(ticker);
        this.bars = ticker.getBars().stream().map(BarDto::new).collect(Collectors.toList());
    }
}
