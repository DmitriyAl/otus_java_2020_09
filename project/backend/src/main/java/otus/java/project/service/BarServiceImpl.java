package otus.java.project.service;

import otus.java.project.dao.BarDao;
import otus.java.project.dto.stock.BarDto;
import otus.java.project.entity.Bar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BarServiceImpl implements BarService {
    private final BarDao barDao;

    @Autowired
    public BarServiceImpl(BarDao barDao) {
        this.barDao = barDao;
    }

    @Override
    public BarDto findBar(String symbol, Date date) {
        Optional<Bar> optional = barDao.findTopByTickerSymbolAndDateLessThanEqualOrderByDateDesc(symbol, date);
        if (optional.isEmpty()) {
            return null;
        }
        return new BarDto(optional.get());
    }

    @Override
    public List<BarDto> getLastBars(List<String> tickers) {
        Date date = new Date();
        List<BarDto> bars = new ArrayList<>();
        for (String ticker : tickers) {
            barDao.findTopByTickerSymbolAndDateLessThanEqualOrderByDateDesc(ticker, date)
                    .ifPresent(b -> bars.add(new BarDto(b)));
        }
        return bars;
    }
}
