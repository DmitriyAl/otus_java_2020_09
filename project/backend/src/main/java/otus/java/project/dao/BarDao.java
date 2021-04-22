package otus.java.project.dao;

import otus.java.project.entity.Bar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BarDao extends JpaRepository<Bar, Long> {
    List<Bar> findAllByTickerSymbolOrderByDate(String symbol);

    Optional<Bar> findTopByTickerSymbolAndDateLessThanEqualOrderByDateDesc(String symbol, Date date);
}
