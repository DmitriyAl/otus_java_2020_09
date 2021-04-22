package otus.java.project.service;

import otus.java.project.dto.stock.StockDataDto;

public interface AsyncService {
    void saveTickerData(StockDataDto symbol);
}
