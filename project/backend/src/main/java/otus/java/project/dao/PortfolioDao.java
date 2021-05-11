package otus.java.project.dao;

import otus.java.project.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioDao extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findAllByUserId(long userId);

    Optional<Portfolio> findByIdAndUserId(long portfolioId, long userId);
}
