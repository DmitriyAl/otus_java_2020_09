package otus.java.tomcat.dao;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otus.java.tomcat.base.AbstractHibernateTest;
import otus.java.tomcat.model.Address;
import otus.java.tomcat.model.Phone;
import otus.java.tomcat.model.User;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OldUserDaoTest extends AbstractHibernateTest {
    private Dao<User, Long> userDao;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        userDao = new UserDao(sessionManager);
    }

    @Test
    public void findNoUsers() {
        sessionManager.beginSession();
        assertThat(userDao.findAll()).isEmpty();
        sessionManager.commitSession();
    }

    @Test
    void findOneUser() {
        final User mark = new User("Mark");
        mark.setAddress(new Address("Some street", mark));
        mark.setPhones(Collections.singletonList(new Phone("+79519876543", mark)));

        sessionManager.beginSession();

        final Long markId = userDao.insertOrUpdate(mark);

        assertThat(userDao.findAll()).hasSize(1).containsOnly(mark);
        assertThat(userDao.findById(markId)).isNotEmpty().get().isEqualTo(mark);

        sessionManager.commitSession();
    }

    @Test
    void findTwoUser() {
        final User mark = new User("Mark");
        mark.setAddress(new Address("Some street", mark));
        mark.setPhones(Collections.singletonList(new Phone("+79519876543", mark)));
        final User peter = new User("Peter");
        peter.setAddress(new Address("Another street", peter));
        peter.setPhones(Collections.singletonList(new Phone("+79811234567", peter)));

        sessionManager.beginSession();

        final Long markId = userDao.insertOrUpdate(mark);
        final Long peterId = userDao.insertOrUpdate(peter);

        assertThat(userDao.findAll()).hasSize(2).containsOnly(mark, peter);
        assertThat(userDao.findById(markId)).isNotEmpty().get().isEqualTo(mark);
        assertThat(userDao.findById(peterId)).isNotEmpty().get().isEqualTo(peter);

        sessionManager.commitSession();
    }

    @Test
    void checkLazyInitializationExceptionOutOfTransaction() {
        final User mark = new User("Mark");
        mark.setAddress(new Address("Some street", mark));
        mark.getPhones().add(new Phone("+79519876543", mark));
        mark.getPhones().add(new Phone("+79811234567", mark));

        sessionManager.beginSession();

        final Long markId = userDao.insertOrUpdate(mark);
        sessionManager.commitSession();
        sessionManager.beginSession();
        final Optional<User> optionalUser = userDao.findById(markId);

        sessionManager.commitSession();

        assertThat(optionalUser).isNotEmpty();
        assertThatThrownBy(() -> optionalUser.get().getAddress().getStreet()).isInstanceOf(LazyInitializationException.class);
        assertThatThrownBy(() -> optionalUser.get().getPhones().size()).isInstanceOf(LazyInitializationException.class);
    }

    @Test
    void checkLazyInitializationExceptionInsideAnotherTransaction() {
        final User mark = new User("Mark");
        mark.setAddress(new Address("Some street", mark));
        final Phone phone1 = new Phone("+79519876543", mark);
        mark.getPhones().add(phone1);
        final Phone phone2 = new Phone("+79811234567", mark);
        mark.getPhones().add(phone2);

        sessionManager.beginSession();

        final Long markId = userDao.insertOrUpdate(mark);
        sessionManager.commitSession();
        sessionManager.beginSession();
        final Optional<User> optionalUser = userDao.findById(markId);
        sessionManager.commitSession();
        sessionManager.beginSession();

        assertThat(optionalUser).isNotEmpty();
        assertThatThrownBy(() -> optionalUser.get().getAddress().getStreet()).isInstanceOf(LazyInitializationException.class);
        assertThatThrownBy(() -> optionalUser.get().getPhones().size()).isInstanceOf(LazyInitializationException.class);
        sessionManager.commitSession();
    }

    @Test
    void checkLazyInitialization() {
        final User mark = new User("Mark");
        mark.setAddress(new Address("Some street", mark));
        final Phone phone1 = new Phone("+79519876543", mark);
        mark.getPhones().add(phone1);
        final Phone phone2 = new Phone("+79811234567", mark);
        mark.getPhones().add(phone2);

        sessionManager.beginSession();

        final Long markId = userDao.insertOrUpdate(mark);
        final Optional<User> optionalUser = userDao.findById(markId);

        assertThat(optionalUser).isNotEmpty();
        assertThat(optionalUser.get().getAddress().getStreet()).isEqualTo(mark.getAddress().getStreet());
        assertThat(optionalUser.get().getPhones()).hasSize(2).containsOnly(phone1, phone2);
        sessionManager.commitSession();
    }
}