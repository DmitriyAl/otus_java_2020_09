package otus.java.project.service;

import otus.java.project.dto.calculations.CalculationsDto;
import otus.java.project.dto.calculations.InputParamsDto;

public interface CalculationsService {
    CalculationsDto calculate(InputParamsDto params);
}
