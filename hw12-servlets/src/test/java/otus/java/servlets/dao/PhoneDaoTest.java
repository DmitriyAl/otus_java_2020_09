package otus.java.servlets.dao;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otus.java.servlets.base.AbstractHibernateTest;
import otus.java.servlets.model.Address;
import otus.java.servlets.model.Phone;
import otus.java.servlets.model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PhoneDaoTest extends AbstractHibernateTest {
    private Dao<Phone, UUID> phoneDao;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        phoneDao = new PhoneDao(sessionManager);
    }

    @Test
    public void findNoPhones() {
        sessionManager.beginSession();
        assertThat(phoneDao.findAll()).isEmpty();
        sessionManager.commitSession();
    }

    @Test
    void findOnePhone() {
        final User mark = new User("Mark");
        mark.setAddress(new Address("Some street", mark));
        final Phone phone = new Phone("+79519876543", mark);
        mark.setPhones(Collections.singletonList(phone));

        sessionManager.beginSession();

        final UUID phoneId = phoneDao.insertOrUpdate(phone);

        assertThat(phoneDao.findAll()).hasSize(1).containsOnly(phone);
        assertThat(phoneDao.findById(phoneId)).isNotEmpty().get().isEqualTo(phone);

        sessionManager.commitSession();
    }

    @Test
    void findTwoPhones() {
        final User mark = new User("Mark");
        mark.setAddress(new Address("Some street", mark));
        final Phone phone1 = new Phone("+79519876543", mark);
        final Phone phone2 = new Phone("+79811234567", mark);
        mark.setPhones(Arrays.asList(phone1, phone2));

        sessionManager.beginSession();

        final UUID phone1Id = phoneDao.insertOrUpdate(phone1);

        assertThat(phoneDao.findAll()).hasSize(2).containsOnly(phone1, phone2);
        assertThat(phoneDao.findById(phone1Id)).isNotEmpty().get().isEqualTo(phone1);
        assertThat(phoneDao.findById(phone2.getId())).isNotEmpty().get().isEqualTo(phone2);

        sessionManager.commitSession();
    }

    @Test
    void checkLazyInitializationExceptionOutOfTransaction() {
        final User mark = new User("Mark");
        mark.setAddress(new Address("Some street", mark));
        final Phone phone = new Phone("+79519876543", mark);
        mark.getPhones().add(phone);

        sessionManager.beginSession();

        final UUID phoneId = phoneDao.insertOrUpdate(phone);
        sessionManager.commitSession();
        sessionManager.beginSession();
        final Optional<Phone> optionalPhone = phoneDao.findById(phoneId);

        sessionManager.commitSession();

        assertThat(optionalPhone).isNotEmpty();
        assertThatThrownBy(() -> optionalPhone.get().getUser().getName()).isInstanceOf(LazyInitializationException.class);
    }

    @Test
    void checkLazyInitializationExceptionInsideAnotherTransaction() {
        final User mark = new User("Mark");
        mark.setAddress(new Address("Some street", mark));
        final Phone phone1 = new Phone("+79519876543", mark);
        mark.getPhones().add(phone1);

        sessionManager.beginSession();

        final UUID phoneId = phoneDao.insertOrUpdate(phone1);
        sessionManager.commitSession();
        sessionManager.beginSession();
        final Optional<Phone> optionalPhone = phoneDao.findById(phoneId);
        sessionManager.commitSession();
        sessionManager.beginSession();

        assertThat(optionalPhone).isNotEmpty();
        assertThatThrownBy(() -> optionalPhone.get().getUser().getName()).isInstanceOf(LazyInitializationException.class);
        sessionManager.commitSession();
    }

    @Test
    void checkLazyInitialization() {
        final User mark = new User("Mark");
        mark.setAddress(new Address("Some street", mark));
        final Phone phone1 = new Phone("+79519876543", mark);
        mark.getPhones().add(phone1);

        sessionManager.beginSession();

        final UUID phoneId = phoneDao.insertOrUpdate(phone1);
        final Optional<Phone> optionalPhone = phoneDao.findById(phoneId);

        assertThat(optionalPhone).isNotEmpty();
        assertThat(optionalPhone.get().getUser().getName()).isEqualTo(mark.getName());
        sessionManager.commitSession();
    }
}