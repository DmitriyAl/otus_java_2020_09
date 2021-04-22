package otus.java.project.dao;

import otus.java.project.entity.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TickerDao extends JpaRepository<Ticker, Long> {
    Optional<Ticker> findBySymbol(String symbol);
}
