package otus.java.messages.cache;

public interface CacheListener<K, V> {
    void notify(K key, V value, CacheEvent event);
}