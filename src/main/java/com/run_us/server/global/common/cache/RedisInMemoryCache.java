package com.run_us.server.global.common.cache;

import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@RequiredArgsConstructor
public class RedisInMemoryCache<K, V> implements InMemoryCache<K, V> {
    private final RedisTemplate<K, CacheEntry<V>> cache;

    @Override
    public void put(K key, V value) {
        CacheEntry<V> entry = CacheEntry.permanent(value);
        cache.opsForValue().set(key, entry);
    }

    @Override
    public void put(K key, V value, Duration ttl) {
        CacheEntry<V> entry = CacheEntry.withTtl(value, ttl);
        cache.opsForValue().set(key, entry, ttl);
    }

    @Override
    public boolean putIfAbsent(K key, V value) {
        return Boolean.TRUE.equals(
            cache.opsForValue().setIfAbsent(key, CacheEntry.permanent(value)));
    }

    @Override
    public boolean putIfAbsent(K key, V value, Duration ttl) {
        return Boolean.TRUE.equals(
            cache.opsForValue().setIfAbsent(key, CacheEntry.withTtl(value, ttl), ttl));
    }

    @Override
    public Optional<V> get(K key) {
        CacheEntry<V> entry = cache.opsForValue().get(key);
        if (entry == null) {
            return Optional.empty();
        }
        return Optional.of(entry.value());
    }

    @Override
    public Optional<CacheEntry<V>> getEntry(K key) {
        CacheEntry<V> entry = cache.opsForValue().get(key);
        if (entry == null) {
            return Optional.empty();
        }
        return Optional.of(entry);
    }

    @Override
    public void remove(K key) {
        cache.delete(key);
    }
}
