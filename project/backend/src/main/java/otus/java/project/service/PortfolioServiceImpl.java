package otus.java.project.service;

import otus.java.project.dao.PortfolioDao;
import otus.java.project.dto.portfolio.PortfolioDto;
import otus.java.project.entity.Portfolio;
import otus.java.project.exception.PortfolioException;
import otus.java.project.security.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioDao portfolioDao;

    @Autowired
    public PortfolioServiceImpl(PortfolioDao portfolioDao) {
        this.portfolioDao = portfolioDao;
    }

    public PortfolioDto findPortfolio(long portfolioId, long userId) throws PortfolioException {
        return new PortfolioDto(portfolioDao.findByIdAndUserId(portfolioId, userId).orElseThrow(() -> new PortfolioException("No such portfolio")));
    }

    public List<PortfolioDto> findAllPortfolios(long userId) {
        return portfolioDao.findAllByUserId(userId).stream().map(PortfolioDto::new).collect(Collectors.toList());
    }

    @Override
    public PortfolioDto savePortfolio(PortfolioDto dto, UserPrincipal principal) throws PortfolioException {
        Portfolio portfolio = new Portfolio(dto);
        portfolio.setCreated(dto.getId() == 0 ? new Date() : dto.getCreated());
        portfolio.setUpdated(new Date());
        portfolio.setUser(principal.getUser());
        try {
            return new PortfolioDto(portfolioDao.save(portfolio));
        } catch (Exception ex) {
            throw new PortfolioException("Portfolio name must be unique");
        }
    }

    @Override
    public void deletePortfolio(long id) {
        portfolioDao.deleteById(id);
    }
}
