package otus.java.project.dto.symbol;

import lombok.Data;

import java.util.List;

@Data
public class SearchSymbolDto {
    private List<SymbolDto> bestMatches;
}
