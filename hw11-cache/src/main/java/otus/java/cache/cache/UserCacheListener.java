package otus.java.cache.cache;

import otus.java.cache.model.Address;
import otus.java.cache.model.Phone;
import otus.java.cache.model.User;

import java.util.UUID;

public class UserCacheListener implements CacheListener<Long, User> {
    private final Cache<UUID, Phone> phoneCache;
    private final Cache<Long, Address> addressCache;

    public UserCacheListener(Cache<UUID, Phone> phoneCache, Cache<Long, Address> addressCache) {
        this.phoneCache = phoneCache;
        this.addressCache = addressCache;
    }

    @Override
    public void notify(Long key, User user, CacheEvent event) {
        user.getPhones().forEach(p -> phoneCache.remove(p.getId()));
        addressCache.remove(user.getAddress().getId());
    }
}
