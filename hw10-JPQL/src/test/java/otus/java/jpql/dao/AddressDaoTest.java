package otus.java.jpql.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otus.java.jpql.base.AbstractHibernateTest;
import otus.java.jpql.model.Address;
import otus.java.jpql.model.Phone;
import otus.java.jpql.model.User;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AddressDaoTest extends AbstractHibernateTest {
    private Dao<Address, Long> addressDao;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        addressDao = new AddressDao(sessionManager);
    }

    @Test
    public void findNoAddresses() {
        sessionManager.beginSession();
        assertThat(addressDao.findAll()).isEmpty();
        sessionManager.commitSession();
    }

    @Test
    void findOneAddress() {
        final User mark = new User("Mark");
        final Address street = new Address("Some street", mark);
        mark.setAddress(street);
        mark.setPhones(Collections.singletonList(new Phone("+79519876543", mark)));

        sessionManager.beginSession();

        final Long streetId = addressDao.insertOrUpdate(street);

        assertThat(addressDao.findAll()).hasSize(1).containsOnly(street);
        assertThat(addressDao.findById(streetId)).isNotEmpty().get().isEqualTo(street);

        sessionManager.commitSession();
    }

    @Test
    void findTwoAddress() {
        final User mark = new User("Mark");
        final Address someStreet = new Address("Some street", mark);
        mark.setAddress(someStreet);
        mark.setPhones(Collections.singletonList(new Phone("+79519876543", mark)));
        final User peter = new User("Peter");
        final Address anotherStreet = new Address("Another street", peter);
        peter.setAddress(anotherStreet);
        peter.setPhones(Collections.singletonList(new Phone("+79811234567", peter)));

        sessionManager.beginSession();

        final Long someStreetId = addressDao.insertOrUpdate(someStreet);
        final Long anotherStreetId = addressDao.insertOrUpdate(anotherStreet);

        assertThat(addressDao.findAll()).hasSize(2).containsOnly(someStreet, anotherStreet);
        assertThat(addressDao.findById(someStreetId)).isNotEmpty().get().isEqualTo(someStreet);
        assertThat(addressDao.findById(anotherStreetId)).isNotEmpty().get().isEqualTo(anotherStreet);

        sessionManager.commitSession();
    }

    @Test
    void checkEagerInitialization() {
        final User mark = new User("Mark");
        final Address street = new Address("Some street", mark);
        mark.setAddress(street);
        mark.getPhones().add(new Phone("+79519876543", mark));
        mark.getPhones().add(new Phone("+79811234567", mark));

        sessionManager.beginSession();

        final Long streetId = addressDao.insertOrUpdate(street);
        final Optional<Address> optionalAddress = addressDao.findById(streetId);

        sessionManager.commitSession();

        assertThat(optionalAddress).isNotEmpty();
        assertThat(optionalAddress.get().getUser().getName()).isEqualTo(mark.getName());
    }
}