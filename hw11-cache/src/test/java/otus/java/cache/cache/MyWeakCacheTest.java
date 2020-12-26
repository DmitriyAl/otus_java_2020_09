package otus.java.cache.cache;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.Invocation;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MyWeakCacheTest {
    @Mock
    private CacheListener<Long, String> listener;

    @Test
    void checkCreated() {
        final MyWeakCache<Long, String> cache = new MyWeakCache<>();
        cache.addListener(listener);
        cache.put(1L, "one");
        verify(listener, times(1)).notify(1L, "one", CacheEvent.ADDED);
    }

    @Test
    void checkNoValue() throws InterruptedException {
        final MyWeakCache<Long, String> cache = new MyWeakCache<>();
        cache.addListener(listener);
        cache.put(1L, "one");
        System.gc();
        TimeUnit.MILLISECONDS.sleep(1);
        assertThat(cache.get(1L)).isNull();
        verify(listener, times(1)).notify(1L, "one", CacheEvent.ADDED);
    }

    @Test
    void checkNoUpdated() throws InterruptedException {
        final MyWeakCache<Long, String> cache = new MyWeakCache<>();
        cache.addListener(listener);
        cache.put(1L, "old");
        System.gc();
        TimeUnit.MILLISECONDS.sleep(1);
        cache.put(1L, "new");
        verify(listener, times(1)).notify(1L, "old", CacheEvent.ADDED);
        verify(listener, times(1)).notify(1L, "new", CacheEvent.ADDED);
    }

    @Test
    void checkUpdated() throws InterruptedException {
        final MyWeakCache<Long, String> cache = new MyWeakCache<>();
        cache.addListener(listener);
        cache.put(1L, "old");
        cache.put(1L, "new");
        final Collection<Invocation> invocations = mockingDetails(listener).getInvocations();
        assertThat(invocations.size()).isEqualTo(2);
    }
}