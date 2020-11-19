package otus.java.atm.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otus.java.atm.model.Dignity;
import otus.java.atm.model.MoneyPack;

import static org.assertj.core.api.Assertions.assertThat;

public class EmptyAtmTest {
    private Atm atm;

    @BeforeEach
    void initDefaultAtm() {
        this.atm = AtmFactory.getAtm();
    }

    @Test
    void checkBalanceEmptyAtm() {
        assertThat(atm.getBalance()).hasSize(0);
        assertThat(atm.getTotalSum()).isEqualTo(0);
    }

    @Test
    void addMoney() {
        var moneyPack1 = new MoneyPack(Dignity.RUB_100, 3);
        atm.addMoney(moneyPack1);
        assertThat(atm.getBalance()).hasSize(1).contains(moneyPack1);
        assertThat(atm.getTotalSum()).isEqualTo(300);
        var moneyPack2 = new MoneyPack(Dignity.RUB_200, 2);
        atm.addMoney(moneyPack2);
        assertThat(atm.getBalance()).hasSize(2).contains(moneyPack1, moneyPack2);
        assertThat(atm.getTotalSum()).isEqualTo(700);
        var moneyPack3 = new MoneyPack(Dignity.RUB_100, 4);
        atm.addMoney(moneyPack3);
        assertThat(atm.getBalance()).hasSize(2).contains(new MoneyPack(Dignity.RUB_100, 7), moneyPack2);
        assertThat(atm.getTotalSum()).isEqualTo(1100);
    }
}
