package otus.java.cache.cache;

import otus.java.cache.model.Phone;
import otus.java.cache.model.User;

import java.util.UUID;

public class PhoneCacheListener implements CacheListener<UUID, Phone> {
    private final Cache<Long, User> userCache;

    public PhoneCacheListener(Cache<Long, User> userCache) {
        this.userCache = userCache;
    }

    @Override
    public void notify(UUID key, Phone phone, CacheEvent event) {
        userCache.remove(phone.getUser().getId());
    }
}
