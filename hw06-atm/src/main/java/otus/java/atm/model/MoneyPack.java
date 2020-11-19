package otus.java.atm.model;

public class MoneyPack implements Comparable<MoneyPack> {
    private final Dignity dignity;
    private final long amount;

    public MoneyPack(Dignity dignity, long amount) {
        if (dignity == null) {
            throw new IllegalArgumentException("Dignity can't be null");
        }
        this.dignity = dignity;
        this.amount = amount;
    }

    public Dignity getDignity() {
        return dignity;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoneyPack moneyPack = (MoneyPack) o;

        if (amount != moneyPack.amount) return false;
        return dignity == moneyPack.dignity;
    }

    @Override
    public int hashCode() {
        int result = dignity.hashCode();
        result = 31 * result + (int) (amount ^ (amount >>> 32));
        return result;
    }

    @Override
    public int compareTo(MoneyPack biggerPack) {
        return biggerPack.dignity.getValue() - this.dignity.getValue();
    }

    @Override
    public String toString() {
        return String.format("%d banknotes by %d dignity", amount, dignity.getValue());
    }
}
