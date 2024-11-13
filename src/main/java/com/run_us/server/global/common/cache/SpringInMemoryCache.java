package com.run_us.server.global.common.cache;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

public class SpringInMemoryCache<K, V> implements InMemoryCache<K, V>, InitializingBean, DisposableBean {
    private final ConcurrentHashMap<K, CacheEntry<V>> cache = new ConcurrentHashMap<>();
    private final TaskScheduler taskScheduler;
    private final Duration cleanupInterval;
    private ScheduledFuture<?> cleanupTask;

    public SpringInMemoryCache(TaskScheduler taskScheduler, Duration cleanupInterval) {
        this.taskScheduler = taskScheduler;
        this.cleanupInterval = cleanupInterval;
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, CacheEntry.permanent(value));
    }

    @Override
    public void put(K key, V value, Duration ttl) {
        cache.put(key, CacheEntry.withTtl(value, ttl));
    }

    @Override
    public boolean putIfAbsent(K key, V value) {
        return cache.putIfAbsent(key, CacheEntry.permanent(value)) == null;
    }

    @Override
    public boolean putIfAbsent(K key, V value, Duration ttl) {
        CacheEntry<V> newEntry = CacheEntry.withTtl(value, ttl);
        CacheEntry<V> existingEntry = cache.putIfAbsent(key, newEntry);

        if (existingEntry != null) {
            if (existingEntry.isExpired()) {
                cache.put(key, newEntry);
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public Optional<V> get(K key) {
        CacheEntry<V> entry = cache.get(key);
        if (entry == null) {
            return Optional.empty();
        }

        if (entry.isExpired()) {
            cache.remove(key);
            return Optional.empty();
        }

        return Optional.of(entry.value());
    }

    @Override
    public Optional<CacheEntry<V>> getEntry(K key) {
        CacheEntry<V> entry = cache.get(key);
        if (entry == null || entry.isExpired()) {
            if (entry != null && entry.isExpired()) {
                cache.remove(key);
            }
            return Optional.empty();
        }
        return Optional.of(entry);
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
    }

    @Override
    public void cleanup() {
        cache.entrySet().removeIf(entry ->
                        entry.getValue().expiresAt() != null &&
                        entry.getValue().isExpired());
    }

    @Override
    public void afterPropertiesSet() {
        this.cleanupTask = taskScheduler.scheduleAtFixedRate(
                this::cleanup,
                cleanupInterval
        );
    }

    @Override
    public void destroy() {
        if (cleanupTask != null) {
            cleanupTask.cancel(false);
        }
    }
}
