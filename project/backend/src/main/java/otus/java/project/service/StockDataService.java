package otus.java.project.service;

import otus.java.project.dto.stock.StockDataDto;
import otus.java.project.dto.symbol.SymbolDto;
import otus.java.project.exception.TickerException;

import java.util.List;

public interface StockDataService {
    List<SymbolDto> findMatchingTickers(String ticker);

    StockDataDto getTickerData(String ticker) throws TickerException;
}
