package otus.java.cache.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otus.java.cache.base.AbstractHibernateTest;
import otus.java.cache.cache.*;
import otus.java.cache.model.Address;
import otus.java.cache.model.Phone;
import otus.java.cache.model.User;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class NewUserDaoTest extends AbstractHibernateTest {
    private Dao<User, Long> userDao;
    private Dao<Address, Long> addressDao;
    private Dao<Phone, UUID> phoneDao;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        Cache<Long, User> userCache = new MyWeakCache<>();
        Cache<Long, Address> addressCache = new MyWeakCache<>();
        Cache<UUID, Phone> phoneCache = new MyWeakCache<>();
        userCache.addListener(new UserCacheListener(phoneCache, addressCache));
        phoneCache.addListener(new PhoneCacheListener(userCache));
        addressCache.addListener(new AddressCacheListener(userCache));
        userDao = new UserDao(sessionManager, userCache);
        addressDao = new AddressDao(sessionManager, addressCache);
        phoneDao = new PhoneDao(sessionManager, phoneCache);
    }

    @Test
    public void checkUserListener() {
        final User mark = new User("Mark");
        mark.setAddress(new Address("Some street", mark));
        mark.getPhones().add(new Phone("+79519876543", mark));
        mark.getPhones().add(new Phone("+79811234567", mark));
        sessionManager.beginSession();
        final Long markId = userDao.insertOrUpdate(mark);
        sessionManager.commitSession();
        sessionManager.beginSession();
        Optional<User> optional = userDao.findById(markId);
        assertThat(optional).isNotEmpty();
        final User receivedMark = optional.get();
        Long addressId = receivedMark.getAddress().getId();
        UUID phone1Id = receivedMark.getPhones().get(0).getId();
        UUID phone2Id = receivedMark.getPhones().get(1).getId();
        assertThat(addressDao.findById(addressId)).isNotEmpty().get().isEqualTo(receivedMark.getAddress());
        assertThat(phoneDao.findById(phone1Id)).isNotEmpty().get().isEqualTo(receivedMark.getPhones().get(0));
        assertThat(phoneDao.findById(phone2Id)).isNotEmpty().get().isEqualTo(receivedMark.getPhones().get(1));
        sessionManager.commitSession();
        sessionManager.beginSession();
        receivedMark.setName("Carl");
        final Long carlId = userDao.insertOrUpdate(receivedMark);
        optional = userDao.findById(carlId);
        assertThat(optional).isNotEmpty();
        final User receivedCarl = optional.get();
        addressId = receivedCarl.getAddress().getId();
        phone1Id = receivedCarl.getPhones().get(0).getId();
        phone2Id = receivedCarl.getPhones().get(1).getId();
        final Optional<Address> optionalAddress = addressDao.findById(addressId);
        assertThat(optionalAddress).isNotEmpty();
        assertThat(optionalAddress.get().getUser()).isEqualTo(receivedCarl);
        final Optional<Phone> optionalPhone1 = phoneDao.findById(phone1Id);
        assertThat(optionalPhone1).isNotEmpty();
        assertThat(optionalPhone1.get().getUser()).isEqualTo(receivedCarl);
        final Optional<Phone> optionalPhone2 = phoneDao.findById(phone2Id);
        assertThat(optionalPhone2).isNotEmpty();
        assertThat(optionalPhone2.get().getUser()).isEqualTo(receivedCarl);
        sessionManager.commitSession();
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
