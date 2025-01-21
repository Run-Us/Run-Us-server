package com.run_us.server.global.common.cache;

import java.time.Duration;
import java.util.Optional;

public interface InMemoryCache<K, V> {

    void put(K key, V value);
    void put(K key, V value, Duration ttl);

    boolean putIfAbsent(K key, V value);
    boolean putIfAbsent(K key, V value, Duration ttl);

    Optional<V> get(K key);
    Optional<CacheEntry<V>> getEntry(K key);
    void remove(K key);
}