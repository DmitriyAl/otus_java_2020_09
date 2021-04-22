package otus.java.project.dto.symbol;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SymbolDto {
    @JsonProperty("1. symbol")
    private String symbol;
    @JsonProperty("2. name")
    private String name;
    @JsonProperty("3. type")
    private String type;
    @JsonProperty("4. region")
    private String region;
    @JsonProperty("5. marketOpen")
    private String marketOpen;
    @JsonProperty("6. marketClose")
    private String marketClose;
    @JsonProperty("7. timezone")
    private String timeZone;
    @JsonProperty("8. currency")
    private String currency;
    @JsonProperty("9. matchScore")
    private Float matchScore;
}
