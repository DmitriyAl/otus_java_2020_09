package otus.java.atm.model;

import java.util.Comparator;

public enum Dignity implements Comparable<Dignity> {
    RUB_100(100),
    RUB_200(200),
    RUB_500(500),
    RUB_1000(1000),
    RUB_2000(2000),
    RUB_5000(5000);

    private final int value;

    Dignity(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static DignityComparator getComparator() {
        return new DignityComparator();
    }

    public static class DignityComparator implements Comparator<Dignity> {
        private DignityComparator() {
        }

        @Override
        public int compare(Dignity smallerDignity, Dignity biggerDignity) {
            return biggerDignity.getValue() - smallerDignity.getValue();
        }
    }

}
