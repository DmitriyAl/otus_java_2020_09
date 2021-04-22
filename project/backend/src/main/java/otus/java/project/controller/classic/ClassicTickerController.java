package otus.java.project.controller.classic;

import otus.java.project.service.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ClassicTickerController {
    private final StockDataService stockDataService;

    @Autowired
    public ClassicTickerController(StockDataService stockDataService) {
        this.stockDataService = stockDataService;
    }

    @GetMapping("/ticker/{symbol}")
    public String ticker(Model model, @PathVariable("symbol") String symbol) {
        model.addAttribute("symbol", symbol);
        return "ticker";
    }
}
