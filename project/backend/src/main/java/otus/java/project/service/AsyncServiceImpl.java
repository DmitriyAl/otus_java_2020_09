package otus.java.project.service;

import otus.java.project.dao.TickerDao;
import otus.java.project.dto.stock.BarDto;
import otus.java.project.dto.stock.StockDataDto;
import otus.java.project.entity.Bar;
import otus.java.project.entity.Ticker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Async
public class AsyncServiceImpl implements AsyncService {
    private final TickerDao tickerDao;

    @Autowired
    public AsyncServiceImpl(TickerDao tickerDao) {
        this.tickerDao = tickerDao;
    }

    @Override
    public void saveTickerData(StockDataDto dto) {
        try {
            Ticker newTicker = new Ticker();
            newTicker.setInformation(dto.getMetadata().getInformation());
            newTicker.setSymbol(dto.getMetadata().getSymbol());
            newTicker.setLastRefreshed(dto.getMetadata().getLastRefreshed());
            newTicker.setOutputSize(dto.getMetadata().getOutputSize());
            newTicker.setTimeZone(dto.getMetadata().getTimeZone());
            final List<Bar> bars = convertBars(dto.getBars(), newTicker);
            newTicker.setBars(bars);
            tickerDao.save(newTicker);
        } catch (DataIntegrityViolationException exception) {
        }
    }

    private List<Bar> convertBars(List<BarDto> dtos, Ticker ticker) {
        List<Bar> bars = new ArrayList<>();
        for (BarDto dto : dtos) {
            Bar bar = new Bar();
            bar.setDate(dto.getDate());
            bar.setOpen(dto.getOpen());
            bar.setHigh(dto.getHigh());
            bar.setLow(dto.getLow());
            bar.setClose(dto.getClose());
            bar.setAdjustedClose(dto.getAdjustedClose());
            bar.setVolume(dto.getVolume());
            bar.setDividendAmount(dto.getDividendAmount());
            bar.setSplitCoefficient(dto.getSplitCoefficient());
            bar.setTicker(ticker);
            bars.add(bar);
        }
        return bars;
    }
}
