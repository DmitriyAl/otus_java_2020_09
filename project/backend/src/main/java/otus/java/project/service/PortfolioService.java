package otus.java.project.service;

import otus.java.project.dto.portfolio.PortfolioDto;
import otus.java.project.exception.PortfolioException;
import otus.java.project.security.model.UserPrincipal;

import java.util.List;

public interface PortfolioService {
    PortfolioDto findPortfolio(long portfolioId, long userId) throws PortfolioException;

    List<PortfolioDto> findAllPortfolios(long userId);

    PortfolioDto savePortfolio(PortfolioDto portfolio, UserPrincipal principal) throws PortfolioException;

    void deletePortfolio(long id);
}
