package otus.java.cache.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.java.cache.dto.AddressDto;
import otus.java.cache.dto.PhoneDto;
import otus.java.cache.dto.UserDto;

import java.util.UUID;

public class UserCacheListener implements CacheListener<Long, UserDto> {
    private final Cache<UUID, PhoneDto> phoneCache;
    private final Cache<Long, AddressDto> addressCache;

    private static final Logger logger = LoggerFactory.getLogger(UserCacheListener.class);

    public UserCacheListener(Cache<UUID, PhoneDto> phoneCache, Cache<Long, AddressDto> addressCache) {
        this.phoneCache = phoneCache;
        this.addressCache = addressCache;
    }

    @Override
    public void notify(Long key, UserDto user, CacheEvent event) {
        switch (event) {
            case ADDED -> logger.info("User with id {} was added to the cache", user.getId());
            case RECEIVED -> logger.info("User with id {} was received from the cache", user.getId());
            case REMOVED -> {
                logger.info("User with id {} was removed from the cache", user.getId());
                user.getPhones().forEach(p -> phoneCache.remove(p.getId()));
                addressCache.remove(user.getAddress().getId());
            }
        }
    }
}
