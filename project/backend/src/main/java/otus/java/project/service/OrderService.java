package otus.java.project.service;

import otus.java.project.dto.portfolio.OrderDto;
import otus.java.project.exception.PortfolioException;
import otus.java.project.exception.TickerException;

import java.util.List;

public interface OrderService {
    List<OrderDto> findOrders(long portfolioId);

    OrderDto saveOrder(OrderDto orderDto) throws PortfolioException, TickerException;

    void deleteOrder(Long id);
}
