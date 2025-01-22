package com.run_us.server.global.common.cache;

import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@RequiredArgsConstructor
public class RedisInMemoryCache<K, V> implements InMemoryCache<K, V> {
    private final RedisTemplate<K, V> cache;

    @Override
    public void put(K key, V value) {
        cache.opsForValue().set(key, value);
    }

    @Override
    public void put(K key, V value, Duration ttl) {
        cache.opsForValue().set(key, value, ttl);
    }

    @Override
    public boolean putIfAbsent(K key, V value) {
        return Boolean.TRUE.equals(
            cache.opsForValue().setIfAbsent(key, value));
    }

    @Override
    public boolean putIfAbsent(K key, V value, Duration ttl) {
        return Boolean.TRUE.equals(
            cache.opsForValue().setIfAbsent(key, value, ttl));
    }

    @Override
    public Optional<V> get(K key) {
        V value = cache.opsForValue().get(key);
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(value);
    }

    @Override
    public Optional<CacheEntry<V>> getEntry(K key) {
        V value = cache.opsForValue().get(key);
        if (value == null) {
            return Optional.empty();
        }
        Long ttl = cache.getExpire(key);
        return Optional.of(
            CacheEntry.withTtl(value, Duration.ofSeconds(ttl)));
    }

    @Override
    public boolean remove(K key) {
        return Boolean.TRUE.equals(
            cache.delete(key));
    }
}
