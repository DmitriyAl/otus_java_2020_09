package otus.java.messages.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import otus.java.messages.cache.*;
import otus.java.messages.model.Address;
import otus.java.messages.model.Phone;
import otus.java.messages.model.User;
import otus.java.messages.sessionmanager.SessionManagerHibernate;
import otus.java.messages.util.HibernateUtils;
import otus.java.messages.util.MigrationsExecutorFlyway;

@Configuration
public class BeanConfig {

    @Bean
    public MigrationsExecutorFlyway migrationsExecutorFlyway() {
        final MigrationsExecutorFlyway executorFlyway = new MigrationsExecutorFlyway();
        executorFlyway.cleanDb();
        executorFlyway.executeMigrations();
        return executorFlyway;
    }

    @Bean
    public SessionManagerHibernate sessionManager() {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(User.class, Address.class, Phone.class);
        return new SessionManagerHibernate(sessionFactory);
    }

    @Bean
    public UserCacheListener userCacheListener(UserCache userCache, PhoneCache phoneCache, AddressCache addressCache) {
        final UserCacheListener userCacheListener = new UserCacheListener(phoneCache, addressCache);
        userCache.addListener(userCacheListener);
        return userCacheListener;
    }

    @Bean
    public PhoneCacheListener phoneCacheListener(UserCache userCache, PhoneCache phoneCache) {
        final PhoneCacheListener phoneCacheListener = new PhoneCacheListener(userCache);
        phoneCache.addListener(phoneCacheListener);
        return phoneCacheListener;
    }

    @Bean
    public AddressCacheListener addressCacheListener(UserCache userCache, AddressCache addressCache) {
        final AddressCacheListener addressCacheListener = new AddressCacheListener(userCache);
        addressCache.addListener(addressCacheListener);
        return addressCacheListener;
    }
}