package otus.java.project.dto.portfolio;

import otus.java.project.dto.stock.TickerDto;
import otus.java.project.entity.Order;
import otus.java.project.entity.OrderType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private PortfolioDto portfolio;
    private TickerDto ticker;
    private Date executed;
    private int amount;
    private float price;
    private OrderType type;

    public OrderDto(Order dao) {
        this.id = dao.getId();
        this.portfolio = new PortfolioDto(dao.getPortfolio());
        this.ticker = new TickerDto(dao.getTicker());
        this.executed = dao.getExecuted();
        this.amount = dao.getAmount();
        this.price = dao.getPrice();
        this.type = dao.getType();
    }
}
