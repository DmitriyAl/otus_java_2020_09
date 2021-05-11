package otus.java.project.dto.portfolio;

import otus.java.project.entity.Portfolio;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PortfolioDto {
    private long id;
    private String name;
    private Date created;
    private Date updated;

    public PortfolioDto(Portfolio dao) {
        this.id = dao.getId();
        this.name = dao.getName();
        this.created = dao.getCreated();
        this.updated = dao.getUpdated();
    }
}
