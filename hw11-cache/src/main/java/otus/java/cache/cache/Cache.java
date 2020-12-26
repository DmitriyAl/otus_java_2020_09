package otus.java.cache.cache;

import otus.java.cache.model.Replicable;

public interface Cache<K, V> {

    void put(K key, V value);

    void remove(K key);

    V get(K key);

    void addListener(CacheListener<K, V> listener);

    void removeListener(CacheListener<K, V> listener);
}
