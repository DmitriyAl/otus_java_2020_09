package otus.java.tomcat.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.java.tomcat.dto.PhoneDto;
import otus.java.tomcat.dto.UserDto;

import java.util.UUID;

public class PhoneCacheListener implements CacheListener<UUID, PhoneDto> {
    private final Cache<Long, UserDto> userCache;

    private static final Logger logger = LoggerFactory.getLogger(PhoneCacheListener.class);

    public PhoneCacheListener(Cache<Long, UserDto> userCache) {
        this.userCache = userCache;
    }

    @Override
    public void notify(UUID key, PhoneDto phone, CacheEvent event) {
        switch (event) {
            case ADDED -> logger.info("Phone with id {} was added to the cache", phone.getId());
            case RECEIVED -> logger.info("Phone with id {} was received from the cache", phone.getId());
            case REMOVED-> {
                logger.info("Phone with id {} was removed from the cache", phone.getId());
                userCache.remove(phone.getUserId());
            }
        }
    }
}
