package otus.java.cache.cache;

public interface CacheListener<K, V> {
    void notify(K key, V value, CacheEvent event);
}