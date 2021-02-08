package otus.java.tomcat.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyWeakCache<K, V> implements Cache<K, V> {
    private final Map<String, V> cache = new WeakHashMap<>();
    private final List<CacheListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.put(key.toString(), value);
        listeners.forEach(l -> l.notify(key, value, CacheEvent.ADDED));
    }

    @Override
    public void remove(K key) {
        final V removed = cache.remove(key.toString());
        if (removed != null) {
            listeners.forEach(l -> l.notify(key, removed, CacheEvent.REMOVED));
        }
    }

    @Override
    public V get(K key) {
        final V received = cache.get(key.toString());
        if (received != null) {
            listeners.forEach(l -> l.notify(key, received, CacheEvent.RECEIVED));
        }
        return received;
    }

    @Override
    public void addListener(CacheListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(CacheListener<K, V> listener) {
        listeners.remove(listener);
    }
}
