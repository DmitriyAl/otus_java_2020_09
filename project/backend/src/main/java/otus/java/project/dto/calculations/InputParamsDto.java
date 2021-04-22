package otus.java.project.dto.calculations;

import lombok.Data;

import java.util.Date;

@Data
public class InputParamsDto {
    private String symbol;
    private int days;
    private Date start;
    private Date stop;
}
