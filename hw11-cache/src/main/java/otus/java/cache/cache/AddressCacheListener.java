package otus.java.cache.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.java.cache.dto.AddressDto;
import otus.java.cache.dto.UserDto;

public class AddressCacheListener implements CacheListener<Long, AddressDto> {
    private final Cache<Long, UserDto> userCache;

    private static final Logger logger = LoggerFactory.getLogger(AddressCacheListener.class);

    public AddressCacheListener(Cache<Long, UserDto> userCache) {
        this.userCache = userCache;
    }

    @Override
    public void notify(Long key, AddressDto address, CacheEvent event) {
        switch (event) {
            case ADDED -> logger.info("Address with id {} was added to the cache", address.getId());
            case RECEIVED -> logger.info("Address with id {} was received from the cache", address.getId());
            case REMOVED -> {
                logger.info("Address with id {} was removed from the cache", address.getId());
                userCache.remove(address.getUserId());
            }
        }
    }
}
