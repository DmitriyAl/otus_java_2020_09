package otus.java.atm.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otus.java.atm.exception.NotEnoughMoneyException;
import otus.java.atm.model.Dignity;
import otus.java.atm.model.MoneyPack;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultAtmTest {
    private Atm atm;

    @BeforeEach
    void initDefaultAtm() {
        this.atm = AtmFactory.getAtm();
        atm.addMoney(new MoneyPack(Dignity.RUB_100, 10),
                new MoneyPack(Dignity.RUB_200, 10),
                new MoneyPack(Dignity.RUB_500, 10),
                new MoneyPack(Dignity.RUB_1000, 10),
                new MoneyPack(Dignity.RUB_2000, 10),
                new MoneyPack(Dignity.RUB_5000, 10));
    }

    @Test
    void getBalance() {
        assertThat(atm.getBalance())
                .hasSize(6)
                .contains(new MoneyPack(Dignity.RUB_100, 10))
                .contains(new MoneyPack(Dignity.RUB_200, 10))
                .contains(new MoneyPack(Dignity.RUB_500, 10))
                .contains(new MoneyPack(Dignity.RUB_1000, 10))
                .contains(new MoneyPack(Dignity.RUB_2000, 10))
                .contains(new MoneyPack(Dignity.RUB_5000, 10));
        assertThat(atm.getTotalSum()).isEqualTo(88000);
    }

    @Test
    void receiveAll5000() throws NotEnoughMoneyException {
        atm.receiveMoney(50000);
        assertThat(atm.getBalance()).hasSize(5)
                .contains(new MoneyPack(Dignity.RUB_100, 10))
                .contains(new MoneyPack(Dignity.RUB_200, 10))
                .contains(new MoneyPack(Dignity.RUB_500, 10))
                .contains(new MoneyPack(Dignity.RUB_1000, 10))
                .contains(new MoneyPack(Dignity.RUB_2000, 10));
        assertThat(atm.getTotalSum()).isEqualTo(38000);
    }

    @Test
    void receiveAll2000() throws NotEnoughMoneyException {
        atm.receiveMoney(70000);
        assertThat(atm.getBalance()).hasSize(4)
                .contains(new MoneyPack(Dignity.RUB_100, 10))
                .contains(new MoneyPack(Dignity.RUB_200, 10))
                .contains(new MoneyPack(Dignity.RUB_500, 10))
                .contains(new MoneyPack(Dignity.RUB_1000, 10));
        assertThat(atm.getTotalSum()).isEqualTo(18000);
    }

    @Test
    void receiveAll1000() throws NotEnoughMoneyException {
        atm.receiveMoney(80000);
        assertThat(atm.getBalance()).hasSize(3)
                .contains(new MoneyPack(Dignity.RUB_100, 10))
                .contains(new MoneyPack(Dignity.RUB_200, 10))
                .contains(new MoneyPack(Dignity.RUB_500, 10));
        assertThat(atm.getTotalSum()).isEqualTo(8000);
    }

    @Test
    void receiveAll500() throws NotEnoughMoneyException {
        atm.receiveMoney(85000);
        assertThat(atm.getBalance()).hasSize(2)
                .contains(new MoneyPack(Dignity.RUB_100, 10))
                .contains(new MoneyPack(Dignity.RUB_200, 10));
        assertThat(atm.getTotalSum()).isEqualTo(3000);
    }

    @Test
    void receiveAll200() throws NotEnoughMoneyException {
        atm.receiveMoney(87000);
        assertThat(atm.getBalance()).hasSize(1)
                .contains(new MoneyPack(Dignity.RUB_100, 10));
        assertThat(atm.getTotalSum()).isEqualTo(1000);
    }

    @Test
    void receiveWholeSum() throws NotEnoughMoneyException {
        atm.receiveMoney(88000);
        assertThat(atm.getBalance()).hasSize(0);
        assertThat(atm.getTotalSum()).isEqualTo(0);
    }

    @Test
    void tryToReceiveMoreThanExist() {
        Assertions.assertThrows(NotEnoughMoneyException.class,
                () -> atm.receiveMoney(100000));
        assertThat(atm.getBalance())
                .hasSize(6)
                .contains(new MoneyPack(Dignity.RUB_100, 10))
                .contains(new MoneyPack(Dignity.RUB_200, 10))
                .contains(new MoneyPack(Dignity.RUB_500, 10))
                .contains(new MoneyPack(Dignity.RUB_1000, 10))
                .contains(new MoneyPack(Dignity.RUB_2000, 10))
                .contains(new MoneyPack(Dignity.RUB_5000, 10));
        assertThat(atm.getTotalSum()).isEqualTo(88000);
    }
}