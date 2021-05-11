package otus.java.project.service;

import otus.java.project.dao.OrderDao;
import otus.java.project.dao.PortfolioDao;
import otus.java.project.dao.TickerDao;
import otus.java.project.dto.portfolio.OrderDto;
import otus.java.project.entity.Order;
import otus.java.project.entity.Portfolio;
import otus.java.project.entity.Ticker;
import otus.java.project.exception.PortfolioException;
import otus.java.project.exception.TickerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderDao orderDao;
    private final PortfolioDao portfolioDao;
    private final TickerDao tickerDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, PortfolioDao portfolioDao, TickerDao tickerDao) {
        this.orderDao = orderDao;
        this.portfolioDao = portfolioDao;
        this.tickerDao = tickerDao;
    }

    @Override
    public List<OrderDto> findOrders(long portfolioId) {
        return orderDao.findAllByPortfolioIdOrderByExecutedAsc(portfolioId)
                .stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @Override
    public OrderDto saveOrder(OrderDto orderDto) throws PortfolioException, TickerException {
        Portfolio portfolio = portfolioDao.findById(orderDto.getPortfolio().getId())
                .orElseThrow(() -> new PortfolioException("No such portfolio"));
        Ticker ticker = tickerDao.findBySymbol(orderDto.getTicker().getSymbol())
                .orElseThrow(() -> new TickerException("No such ticker"));
        Order order = new Order();
        order.setPortfolio(portfolio);
        order.setTicker(ticker);
        order.setExecuted(orderDto.getExecuted());
        order.setAmount(orderDto.getAmount());
        order.setPrice(orderDto.getPrice());
        order.setType(orderDto.getType());
        return new OrderDto(orderDao.save(order));
    }

    @Override
    public void deleteOrder(Long id) {
        orderDao.deleteById(id);
    }
}
