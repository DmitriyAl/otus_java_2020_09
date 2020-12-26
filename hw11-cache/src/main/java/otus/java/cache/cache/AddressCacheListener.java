package otus.java.cache.cache;

import otus.java.cache.model.Address;
import otus.java.cache.model.User;

public class AddressCacheListener implements CacheListener<Long, Address> {
    private final Cache<Long, User> userCache;

    public AddressCacheListener(Cache<Long, User> userCache) {
        this.userCache = userCache;
    }

    @Override
    public void notify(Long key, Address address, CacheEvent event) {
        userCache.remove(address.getUser().getId());
    }
}
