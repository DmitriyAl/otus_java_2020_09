package otus.java.project.service;

import otus.java.project.dao.BarDao;
import otus.java.project.dao.TickerDao;
import otus.java.project.dto.calculations.CalculationsDto;
import otus.java.project.dto.calculations.InputParamsDto;
import otus.java.project.entity.Bar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalculationsServiceImpl implements CalculationsService {
    private final TickerDao tickerDao;
    private final BarDao barDao;

    @Autowired
    public CalculationsServiceImpl(TickerDao tickerDao, BarDao barDao) {
        this.tickerDao = tickerDao;
        this.barDao = barDao;
    }

    @Override
    public CalculationsDto calculate(InputParamsDto params) {
        List<Bar> bars = barDao.findAllByTickerSymbolOrderByDate(params.getSymbol());
        bars = filterBars(bars, params);
        final CalculationsDto calculations = new CalculationsDto();
        if (!bars.isEmpty()) {
            Bar bar = bars.get(0);
            calculations.setInitialPrice(bar.getClose());
            int totalSplitCoefficient = 1;
            for (int i = 0; i < bars.size(); i++) {
                bar = bars.get(i);
                int splitCoefficient = (int) bar.getSplitCoefficient();
                if (splitCoefficient > 1) {
                    calculations.setStockAmount(calculations.getStockAmount() * splitCoefficient);
                    totalSplitCoefficient *= splitCoefficient;
                }
                if (i % params.getDays() == 0) {
                    calculations.setStockAmount(calculations.getStockAmount() + 1);
                    calculations.setOrdersAmount(calculations.getOrdersAmount() + 1);
                    calculations.setSpentMoney(calculations.getSpentMoney() + bar.getClose());
                }
            }
            Bar lastBar = bars.get(bars.size() - 1);
            calculations.setFinalPrice(lastBar.getClose());
            calculations.setCurrentPrice(lastBar.getClose() * (float) calculations.getStockAmount());
            calculations.setInitialPriceWithSplit(calculations.getInitialPrice() / totalSplitCoefficient);
            calculations.setAveragePrice(calculations.getSpentMoney() / calculations.getStockAmount());
            calculations.setGainPercent(lastBar.getClose() / calculations.getAveragePrice());
            calculations.setPriceChange(lastBar.getClose() / calculations.getInitialPriceWithSplit());
            calculations.setResultSplitCoefficient(totalSplitCoefficient);
        }
        return calculations;
    }

    private List<Bar> filterBars(List<Bar> bars, InputParamsDto params) {
        return bars.stream()
                .filter(bar -> bar.getDate().before(params.getStop()))
                .filter(bar -> bar.getDate().after(params.getStart()))
                .collect(Collectors.toList());
    }
}
