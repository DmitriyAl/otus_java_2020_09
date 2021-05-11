package otus.java.project.dto.calculations;

import lombok.Data;

@Data
public class CalculationsDto {
    private float initialPrice;
    private float initialPriceWithSplit;
    private float finalPrice;
    private float spentMoney;
    private float currentPrice;
    private float averagePrice;
    private float gainPercent;
    private float priceChange;
    private int ordersAmount;
    private int stockAmount;
    private int resultSplitCoefficient;
}
