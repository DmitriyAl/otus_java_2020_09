package otus.java.project.service;

import otus.java.project.dao.TickerDao;
import otus.java.project.dto.stock.StockDataDto;
import otus.java.project.dto.symbol.SearchSymbolDto;
import otus.java.project.dto.symbol.SymbolDto;
import otus.java.project.entity.Ticker;
import otus.java.project.exception.TickerException;
import otus.java.project.model.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class StockDataServiceImpl implements StockDataService {
    private final AsyncService asyncService;
    private final TickerDao tickerDao;
    private final RestTemplate restTemplate;
    private final String mainUrl;
    private final String apiKey;

    @Autowired
    public StockDataServiceImpl(AsyncService asyncService,
                                TickerDao tickerDao,
                                RestTemplateBuilder restTemplateBuilder,
                                @Value("${alphavantage.url}") String mainUrl,
                                @Value("${alphavantage.apiKey:demo}") String apiKey) {
        this.asyncService = asyncService;
        this.tickerDao = tickerDao;
        this.restTemplate = restTemplateBuilder.build();
        this.mainUrl = mainUrl;
        this.apiKey = apiKey;
    }

    @Override
    public List<SymbolDto> findMatchingTickers(String ticker) {
        String url = UriComponentsBuilder.fromHttpUrl(mainUrl)
                .queryParam("function", Function.SYMBOL_SEARCH.name())
                .queryParam("keywords", ticker)
                .queryParam("apikey", apiKey).toUriString();
        final ResponseEntity<SearchSymbolDto> entity = restTemplate.getForEntity(url, SearchSymbolDto.class);
        return entity.getBody().getBestMatches();
    }

    @Override
    public StockDataDto getTickerData(String symbol) throws TickerException {
        Optional<Ticker> optional = tickerDao.findBySymbol(symbol);
        if (optional.isPresent()) {
            return new StockDataDto(optional.get());
        }
        String url = UriComponentsBuilder.fromHttpUrl(mainUrl)
                .queryParam("function", Function.TIME_SERIES_DAILY_ADJUSTED.name())
                .queryParam("symbol", symbol)
                .queryParam("outputsize", "full")
                .queryParam("apikey", apiKey).toUriString();
        final ResponseEntity<StockDataDto> entity = restTemplate.getForEntity(url, StockDataDto.class);
        asyncService.saveTickerData(entity.getBody());
        return entity.getBody();
    }
}
