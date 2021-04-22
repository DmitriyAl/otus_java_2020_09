package otus.java.project.controller;

import otus.java.project.dto.portfolio.OrderDto;
import otus.java.project.exception.PortfolioException;
import otus.java.project.exception.TickerException;
import otus.java.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("orders")
    public List<OrderDto> findOrders(@RequestParam("portfolioId") long portfolioId) {
        return orderService.findOrders(portfolioId);
    }

    @PostMapping("orders")
    public OrderDto saveOrder(@RequestBody OrderDto orderDto) throws PortfolioException, TickerException {
        return orderService.saveOrder(orderDto);
    }

    @DeleteMapping("orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
