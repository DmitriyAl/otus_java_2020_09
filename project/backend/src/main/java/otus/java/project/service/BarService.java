package otus.java.project.service;

import otus.java.project.dto.stock.BarDto;

import java.util.Date;
import java.util.List;

public interface BarService {
    BarDto findBar(String symbol, Date date);

    List<BarDto> getLastBars(List<String> tickers);
}
