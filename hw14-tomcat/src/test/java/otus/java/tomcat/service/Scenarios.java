package otus.java.tomcat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import otus.java.tomcat.base.AbstractHibernateTest;
import otus.java.tomcat.cache.*;
import otus.java.tomcat.dao.AddressDao;
import otus.java.tomcat.dao.Dao;
import otus.java.tomcat.dao.PhoneDao;
import otus.java.tomcat.dao.UserDao;
import otus.java.tomcat.dto.AddressDto;
import otus.java.tomcat.dto.PhoneDto;
import otus.java.tomcat.dto.UserDto;
import otus.java.tomcat.model.Address;
import otus.java.tomcat.model.Phone;
import otus.java.tomcat.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    @DisplayName("Compares times for searching by id of 10 users, 10 addresses and 20 numbers with using ot the caches" +
            "and without them.")
    public void checkCacheWithoutUpdate() {
        createTestData();
        checkIfEverythingIsCreated();
        long nonCachedServiceTime = getNotUpdatedGetEverythingTime(userService, phoneService, addressService);
        long cachedServiceTime = getNotUpdatedGetEverythingTime(cachedUserService, cachedPhoneService, cachedAddressService);
        checkIfResultDataCorrect("Mark", "+7911", "Some street");
        displayTimes(nonCachedServiceTime, cachedServiceTime);
    }

    @Test
    @DisplayName("Compares times for searching by id of 10 users, 10 addresses and 20 numbers with using ot the caches" +
            "and without them. Each second launch a user is updated to invalidate caches")
    public void checkCacheWithPartialUpdate() {
        createTestData();
        checkIfEverythingIsCreated();
        long nonCachedServiceTime = getUpdatedGetEverythingTime(userService, phoneService, addressService);
        long cachedServiceTime = getUpdatedGetEverythingTime(cachedUserService, cachedPhoneService, cachedAddressService);
        checkIfResultDataCorrect("Carl", "+7999", "New street");
        displayTimes(nonCachedServiceTime, cachedServiceTime);
    }

    private void createTestData() {
        for (int i = 0; i < 10; i++) {
            final User mark = new User("Mark" + i);
            mark.setAddress(new Address("Some street" + i, mark));
            mark.getPhones().add(new Phone("+79111987643", mark));
            mark.getPhones().add(new Phone("+79111234567", mark));
            userService.save(mark);
        }
    }

    private void checkIfEverythingIsCreated() {
        List<PhoneDto> phones = phoneService.getAll();
        List<UserDto> users = userService.getAll();
        List<AddressDto> addresses = addressService.getAll();

        assertThat(phones).hasSize(20).extracting(PhoneDto::getUserId)
                .allMatch(id -> id >= 1).allMatch(id -> id <= 20);
        assertThat(phones).hasSize(20).extracting(PhoneDto::getNumber).allMatch(p -> p.startsWith("+7911"));
        assertThat(addresses).hasSize(10).extracting(AddressDto::getStreet).allMatch(a -> a.startsWith("Some street"));
        assertThat(addresses).hasSize(10).extracting(AddressDto::getUserId)
                .allMatch(id -> id >= 1).allMatch(id -> id <= 20);
        assertThat(users).hasSize(10).extracting(UserDto::getName).allMatch(n -> n.startsWith("Mark"));
    }

    private long getNotUpdatedGetEverythingTime(DbService<UserDto, User, Long> userService,
                                                DbService<PhoneDto, Phone, UUID> phoneService,
                                                DbService<AddressDto, Address, Long> addressService) {
        long startTime = Calendar.getInstance().getTimeInMillis();
        for (int i = 0; i < 10; i++) {
            for (int j = 1; j <= 10; j++) {
                final UserDto userDto = userService.getById((long) j);
                for (PhoneDto phone : userDto.getPhones()) {
                    phoneService.getById(phone.getId());
                }
                addressService.getById(userDto.getAddress().getId());
            }
        }
        long finishTime = Calendar.getInstance().getTimeInMillis();
        return finishTime - startTime;
    }

    private long getUpdatedGetEverythingTime(DbService<UserDto, User, Long> userService,
                                             DbService<PhoneDto, Phone, UUID> phoneService,
                                             DbService<AddressDto, Address, Long> addressService) {
        long startTime = Calendar.getInstance().getTimeInMillis();
        for (int i = 0; i < 10; i++) {
            for (int j = 1; j <= 10; j++) {
                UserDto userDto = userService.getById((long) j);
                if (i % 5 == 0) {
                    updateUser(j, userDto, userService);
                    userDto = userService.getById(userDto.getId());
                }
                for (PhoneDto phone : userDto.getPhones()) {
                    phoneService.getById(phone.getId());
                }
                addressService.getById(userDto.getAddress().getId());
            }
        }
        long finishTime = Calendar.getInstance().getTimeInMillis();
        return finishTime - startTime;
    }

    private void updateUser(int i, UserDto userDto, DbService<UserDto, User, Long> userService) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName("Carl" + i);
        user.setAddress(new Address("New street" + i, user));
        user.getPhones().add(new Phone("+79992765465", user));
        user.getPhones().add(new Phone("+79998473646", user));
        userService.save(user);
    }

    private void checkIfResultDataCorrect(String name, String phone, String address) {
        List<PhoneDto> phonesInDb = phoneService.getAll();
        List<AddressDto> addressesInDb = addressService.getAll();
        assertThat(phonesInDb).hasSize(20);
        assertThat(addressesInDb).hasSize(10);

        List<PhoneDto> phones = new ArrayList<>();
        List<UserDto> users = new ArrayList<>();
        List<AddressDto> addresses = new ArrayList<>();

        for (int j = 1; j <= 10; j++) {
            final UserDto userDto = userService.getById((long) j);
            users.add(userDto);
            for (PhoneDto phoneDto : userDto.getPhones()) {
                phones.add(phoneService.getById(phoneDto.getId()));
            }
            addresses.add(addressService.getById(userDto.getAddress().getId()));
        }

        assertThat(phones).hasSize(20).extracting(PhoneDto::getUserId)
                .allMatch(id -> id >= 1).allMatch(id -> id <= 20);
        assertThat(phones).hasSize(20).extracting(PhoneDto::getNumber).allMatch(p -> p.startsWith(phone));
        assertThat(phonesInDb).extracting(PhoneDto::getId)
                .containsAll(phones.stream().map(PhoneDto::getId).collect(Collectors.toList()));
        assertThat(addresses).hasSize(10).extracting(AddressDto::getStreet).allMatch(a -> a.startsWith(address));
        assertThat(addresses).hasSize(10).extracting(AddressDto::getUserId)
                .allMatch(id -> id >= 1).allMatch(id -> id <= 20);
        assertThat(addressesInDb).extracting(AddressDto::getId)
                .containsAll(addresses.stream().map(AddressDto::getId).collect(Collectors.toList()));
        assertThat(users).hasSize(10).extracting(UserDto::getName).allMatch(n -> n.startsWith(name));
    }

    private void displayTimes(long nonCachedServiceTime, long cachedServiceTime) {
        System.out.println("Non cached time = " + nonCachedServiceTime);
        System.out.println("Cached time = " + cachedServiceTime);
        assertThat(cachedServiceTime).isLessThan(nonCachedServiceTime);
    }
}
