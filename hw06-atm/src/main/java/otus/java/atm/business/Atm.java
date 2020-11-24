package otus.java.atm.business;

import otus.java.atm.exception.NotEnoughMoneyException;
import otus.java.atm.model.Dignity;
import otus.java.atm.model.MoneyPack;
import otus.java.atm.strategy.CalculateSumStrategy;

import java.util.*;
import java.util.stream.Collectors;

public class Atm {
    private final Map<Dignity, Long> holder;
    private CalculateSumStrategy calculateSumStrategy;

    Atm(CalculateSumStrategy calculateSumStrategy) {
        this.holder = new TreeMap<>(Dignity.getComparator());
        this.calculateSumStrategy = calculateSumStrategy;
    }

    public void addMoney(MoneyPack... packs) {
        Arrays.stream(packs).forEach(p -> holder.computeIfPresent(p.getDignity(), (d, a) -> a += p.getAmount()));
        Arrays.stream(packs).forEach(p -> holder.putIfAbsent(p.getDignity(), p.getAmount()));
    }

    public Set<MoneyPack> getBalance() {
        return holderToMoneyPacks();
    }

    private Set<MoneyPack> holderToMoneyPacks() {
        return holder.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .map(e -> new MoneyPack(e.getKey(), e.getValue()))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public long getTotalSum() {
        return holder.entrySet().stream()
                .map(e -> e.getKey().getValue() * e.getValue())
                .reduce(0L, Long::sum);
    }

    public void receiveMoney(long sum) throws NotEnoughMoneyException {
        if (sum > getTotalSum()) {
            throw new NotEnoughMoneyException("Not enough money in the holder");
        }
        Set<MoneyPack> packs = this.calculateSumStrategy.calculate(holderToMoneyPacks(), sum);
        reduceBalance(packs);
        displayResult(packs, sum);
    }

    private void reduceBalance(Set<MoneyPack> packs) throws NotEnoughMoneyException {
        TreeMap<Dignity, Long> tempHolder = new TreeMap<>(Dignity.getComparator());
        tempHolder.putAll(holder);
        for (MoneyPack pack : packs) {
            takeMoneyFromCell(tempHolder, pack);
        }
        holder.putAll(tempHolder);
    }

    private void takeMoneyFromCell(Map<Dignity, Long> holder, MoneyPack pack) throws NotEnoughMoneyException {
        Dignity dignity = pack.getDignity();
        Long amount = holder.get(dignity);
        if (amount == null || amount < pack.getAmount()) {
            throw new NotEnoughMoneyException(String.format("Not enough '%d' in the holder", dignity.getValue()));
        }
        holder.put(dignity, amount - pack.getAmount());
    }

    private void displayResult(Set<MoneyPack> packs, long sum) {
        System.out.printf("The %d sum was received by %s\n", sum, packs.toString());
    }

    public void setCalculateSumStrategy(CalculateSumStrategy strategy) {
        this.calculateSumStrategy = strategy;
    }
}
