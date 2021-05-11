package otus.java.project.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import otus.java.project.dto.stock.BarDto;
import otus.java.project.dto.stock.StockDataDto;
import otus.java.project.dto.stock.TickerDto;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class StockDataDeserializer extends JsonDeserializer<StockDataDto> {
    private final String METADATA = "Meta Data";

    @Override
    public StockDataDto deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        final StockDataDto stockData = new StockDataDto();
        final TickerDto ticker = new TickerDto();
        List<BarDto> bars = new ArrayList<>();
        fillInMetadata(parser, ticker);
        skipTokens(parser, 4);
        while (!parser.isClosed()) {
            fillInBars(parser, bars);
        }
        stockData.setMetadata(ticker);
        stockData.setBars(bars);
        return stockData;
    }

    private void fillInMetadata(JsonParser parser, TickerDto ticker) throws IOException {
        try {
            skipTokens(parser, 1);
            if (!parser.getText().equals(METADATA)) {
                throw new JsonParseException(parser, "No such ticker");
            }
            skipTokens(parser, 3);
            ticker.setInformation(parser.getText());
            skipTokens(parser, 2);
            ticker.setSymbol(parser.getText());
            skipTokens(parser, 2);
            ticker.setLastRefreshed(new SimpleDateFormat("yyyy-MM-dd").parse(parser.getText()));
            skipTokens(parser, 2);
            ticker.setOutputSize(parser.getText());
            skipTokens(parser, 2);
            ticker.setTimeZone(TimeZone.getTimeZone(parser.getText()));
        } catch (ParseException e) {
            throw new JsonParseException(parser, "Can't parse last refreshed date");
        }
    }

    private void fillInBars(JsonParser parser, List<BarDto> bars) throws IOException {
        try {
            if (JsonToken.END_OBJECT.equals(parser.getCurrentToken())) {
                skipTokens(parser, 1);
                return;
            }
            final BarDto bar = new BarDto();
            bar.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(parser.getCurrentName()));
            skipTokens(parser, 3);
            bar.setOpen(Float.parseFloat(parser.getText()));
            skipTokens(parser, 2);
            bar.setHigh(Float.parseFloat(parser.getText()));
            skipTokens(parser, 2);
            bar.setLow(Float.parseFloat(parser.getText()));
            skipTokens(parser, 2);
            bar.setClose(Float.parseFloat(parser.getText()));
            skipTokens(parser, 2);
            bar.setAdjustedClose(Float.parseFloat(parser.getText()));
            skipTokens(parser, 2);
            bar.setVolume(Integer.parseInt(parser.getText()));
            skipTokens(parser, 2);
            bar.setDividendAmount(Float.parseFloat(parser.getText()));
            skipTokens(parser, 2);
            bar.setSplitCoefficient(Float.parseFloat(parser.getText()));
            bars.add(bar);
            skipTokens(parser, 1);
        } catch (ParseException e) {
            throw new JsonParseException(parser, "Can't parse bar date");
        }
    }

    private void skipTokens(JsonParser parser, int amount) throws IOException {
        for (int i = 0; i < amount; i++) {
            parser.nextToken();
        }
    }
}
