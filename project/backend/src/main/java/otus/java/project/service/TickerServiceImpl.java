package otus.java.project.service;

import otus.java.project.dao.TickerDao;
import otus.java.project.dto.stock.TickerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TickerServiceImpl implements TickerService{
    private final TickerDao tickerDao;

    @Autowired
    public TickerServiceImpl(TickerDao tickerDao) {
        this.tickerDao = tickerDao;
    }

    @Override
    public List<TickerDto> getAllTickers() {
        return tickerDao.findAll().stream().map(TickerDto::new).collect(Collectors.toList());
    }
}
