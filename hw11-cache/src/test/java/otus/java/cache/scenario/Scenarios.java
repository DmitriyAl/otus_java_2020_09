package otus.java.cache.scenario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otus.java.cache.base.AbstractHibernateTest;
import otus.java.cache.cache.*;
import otus.java.cache.dao.AddressDao;
import otus.java.cache.dao.Dao;
import otus.java.cache.dao.PhoneDao;
import otus.java.cache.dao.UserDao;
import otus.java.cache.dto.AddressDto;
import otus.java.cache.dto.PhoneDto;
import otus.java.cache.dto.UserDto;
import otus.java.cache.model.Address;
import otus.java.cache.model.Phone;
import otus.java.cache.model.User;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class Scenarios extends AbstractHibernateTest {
    DbService<UserDto, User, Long> cachedUserService;
    DbService<UserDto, User, Long> userService;
    DbService<PhoneDto, Phone, UUID> cachedPhoneService;
    DbService<PhoneDto, Phone, UUID> phoneService;
    DbService<AddressDto, Address, Long> cachedAddressService;
    DbService<AddressDto, Address, Long> addressService;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        Cache<Long, UserDto> userCache = new MyWeakCache<>();
        Cache<Long, AddressDto> addressCache = new MyWeakCache<>();
        Cache<UUID, PhoneDto> phoneCache = new MyWeakCache<>();
        userCache.addListener(new UserCacheListener(phoneCache, addressCache));
        phoneCache.addListener(new PhoneCacheListener(userCache));
        addressCache.addListener(new AddressCacheListener(userCache));
        Dao<User, Long> userDao = new UserDao(sessionManager);
        Dao<Address, Long> addressDao = new AddressDao(sessionManager);
        Dao<Phone, UUID> phoneDao = new PhoneDao(sessionManager);
        cachedUserService = new DbServiceImpl<>(userDao, userCache);
        cachedPhoneService = new DbServiceImpl<>(phoneDao, phoneCache);
        cachedAddressService = new DbServiceImpl<>(addressDao, addressCache);
        userService = new DbServiceImpl<>(userDao);
        phoneService = new DbServiceImpl<>(phoneDao);
        addressService = new DbServiceImpl<>(addressDao);
    }

    @Test
    public void checkCache() {
        for (int i = 0; i < 10; i++) {
            final User mark = new User("Mark" + i);
            mark.setAddress(new Address("Some street" + i, mark));
            mark.getPhones().add(new Phone("+79519876543", mark));
            mark.getPhones().add(new Phone("+79811234567", mark));
            userService.save(mark);
        }
        final List<PhoneDto> phoneDtoList = phoneService.getAll();
        long startTime = Calendar.getInstance().getTimeInMillis();
        for (int i = 0; i < 10; i++) {
            for (int j = 1; j <= 10; j++) {
                userService.getById((long) j);
                phoneService.getById(phoneDtoList.get(j - 1).getId());
                phoneService.getById(phoneDtoList.get(j + 9).getId());
                addressService.getById((long) j);
            }
        }
        long finishTime = Calendar.getInstance().getTimeInMillis();
        long nonCachedServiceTime = finishTime - startTime;
        startTime = Calendar.getInstance().getTimeInMillis();
        for (int i = 0; i < 10; i++) {
            for (int j = 1; j <= 10; j++) {
                cachedUserService.getById((long) j);
                cachedPhoneService.getById(phoneDtoList.get(j - 1).getId());
                cachedPhoneService.getById(phoneDtoList.get(j + 9).getId());
                cachedAddressService.getById((long) j);
            }
        }
        finishTime = Calendar.getInstance().getTimeInMillis();
        long cachedServiceTime = finishTime - startTime;
        System.out.println("Non cached time = " + nonCachedServiceTime);
        System.out.println("Cached time = " + cachedServiceTime);
        assertThat(cachedServiceTime).isLessThan(nonCachedServiceTime);
    }
}
