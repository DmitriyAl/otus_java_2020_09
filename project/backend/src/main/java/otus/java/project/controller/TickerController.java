package otus.java.project.controller;

import otus.java.project.dto.calculations.CalculationsDto;
import otus.java.project.dto.calculations.InputParamsDto;
import otus.java.project.dto.stock.BarDto;
import otus.java.project.dto.stock.StockDataDto;
import otus.java.project.dto.stock.TickerDto;
import otus.java.project.dto.symbol.SymbolDto;
import otus.java.project.exception.TickerException;
import otus.java.project.service.BarService;
import otus.java.project.service.CalculationsService;
import otus.java.project.service.StockDataService;
import otus.java.project.service.TickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api")
public class TickerController {
    private final StockDataService stockDataService;
    private final CalculationsService calculationsService;
    private final BarService barService;
    private final TickerService tickerService;

    @Autowired
    public TickerController(StockDataService stockDataService, CalculationsService calculationsService, BarService barService, TickerService tickerService) {
        this.stockDataService = stockDataService;
        this.calculationsService = calculationsService;
        this.barService = barService;
        this.tickerService = tickerService;
    }

    @GetMapping("searchTicker")
    public List<SymbolDto> search(@RequestParam("ticker") String ticker) {
        return stockDataService.findMatchingTickers(ticker);
    }

    @PostMapping("calculate")
    public CalculationsDto calculate(@RequestBody InputParamsDto dto) {
        return calculationsService.calculate(dto);
    }

    @GetMapping("ticker/{symbol}")
    public StockDataDto getTickerInfo(@PathVariable("symbol") String symbol) throws TickerException {
        return stockDataService.getTickerData(symbol);
    }

    @GetMapping("tickers")
    public List<TickerDto> getAllTickers() {
        return tickerService.getAllTickers();
    }

    @GetMapping("bar")
    public BarDto searchBar(@RequestParam("symbol") String symbol, @RequestParam("date") Date date) {
        return barService.findBar(symbol, date);
    }

    @GetMapping("lastBars")
    public List<BarDto> getLastBars(@RequestParam("tickers") List<String> tickers) {
        return barService.getLastBars(tickers);
    }
}
