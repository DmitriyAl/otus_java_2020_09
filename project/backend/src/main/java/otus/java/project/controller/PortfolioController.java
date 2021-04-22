package otus.java.project.controller;

import otus.java.project.dto.portfolio.PortfolioDto;
import otus.java.project.exception.PortfolioException;
import otus.java.project.security.model.UserPrincipal;
import otus.java.project.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("portfolios")
    public List<PortfolioDto> findPortfolios(@AuthenticationPrincipal UserPrincipal principal) {
        return portfolioService.findAllPortfolios(principal.getUser().getId());
    }

    @PostMapping("portfolios")
    public PortfolioDto savePortfolio(@AuthenticationPrincipal UserPrincipal principal, @RequestBody PortfolioDto portfolio) throws PortfolioException {
        return portfolioService.savePortfolio(portfolio, principal);
    }

    @GetMapping("portfolio/{id}")
    public PortfolioDto findPortfolio(@AuthenticationPrincipal UserPrincipal principal, @PathVariable("id") long portfolioId) throws PortfolioException {
        return portfolioService.findPortfolio(portfolioId, principal.getUser().getId());
    }

    @DeleteMapping("portfolios/{id}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable("id") long id) {
        portfolioService.deletePortfolio(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
