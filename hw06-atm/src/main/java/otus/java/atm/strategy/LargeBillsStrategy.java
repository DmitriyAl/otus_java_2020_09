package otus.java.atm.strategy;

import otus.java.atm.exception.NotEnoughMoneyException;
import otus.java.atm.model.Dignity;
import otus.java.atm.model.MoneyPack;

import java.util.Set;
import java.util.TreeSet;

public class LargeBillsStrategy implements CalculateSumStrategy {
    @Override
    public Set<MoneyPack> calculate(Set<MoneyPack> holder, long sum) throws NotEnoughMoneyException {
        long initialSum = sum;
        Set<MoneyPack> result = new TreeSet<>();
        for (MoneyPack moneyPack : holder) {
            Dignity dignity = moneyPack.getDignity();
            long reliableAmount = sum / dignity.getValue();
            long availableAmount = moneyPack.getAmount();
            long amount = Math.min(reliableAmount, availableAmount);
            sum -= amount * dignity.getValue();
            result.add(new MoneyPack(dignity, amount));
        }
        if (sum != 0) {
            throw new NotEnoughMoneyException(String.format("The '%d' sum could not be received", initialSum));
        }
        return result;
    }
}
