package otus.java.atm.strategy;

import otus.java.atm.exception.NotEnoughMoneyException;
import otus.java.atm.model.MoneyPack;

import java.util.Set;

public interface CalculateSumStrategy {
    Set<MoneyPack> calculate(Set<MoneyPack> holder, long sum) throws NotEnoughMoneyException;
}
