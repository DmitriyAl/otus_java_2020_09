package otus.java.atm.business;

import otus.java.atm.strategy.LargeBillsStrategy;

public class AtmFactory {

    public static Atm getAtm() {
        return new Atm(new LargeBillsStrategy());
    }
}
