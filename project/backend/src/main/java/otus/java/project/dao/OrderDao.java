package otus.java.project.dao;

import otus.java.project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDao extends JpaRepository<Order, Long> {
    List<Order> findAllByPortfolioIdOrderByExecutedAsc(long id);
}
