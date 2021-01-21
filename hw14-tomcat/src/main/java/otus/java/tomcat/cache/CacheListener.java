package otus.java.tomcat.cache;

public interface CacheListener<K, V> {
    void notify(K key, V value, CacheEvent event);
}