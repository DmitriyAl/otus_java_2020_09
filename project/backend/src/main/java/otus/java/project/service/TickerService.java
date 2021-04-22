package otus.java.project.service;

import otus.java.project.dto.stock.TickerDto;

import java.util.List;

public interface TickerService {
    List<TickerDto> getAllTickers();
}
