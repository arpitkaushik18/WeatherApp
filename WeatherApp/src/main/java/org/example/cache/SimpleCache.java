package org.example.cache;

import java.util.HashMap;
import java.util.Map;

public class SimpleCache<K, V> {

    private final long cacheDurationInMillis;
    private final Map<K, CacheEntry<V>> cacheMap;

    public SimpleCache(long cacheDurationInMillis) {
        this.cacheDurationInMillis = cacheDurationInMillis;
        this.cacheMap = new HashMap<>();
    }

    public synchronized void put(K key, V value) {
        cacheMap.put(key, new CacheEntry<>(value, System.currentTimeMillis()));
    }

    public synchronized V get(K key) {
        CacheEntry<V> entry = cacheMap.get(key);
        if (entry != null && !isExpired(entry)) {
            return entry.getValue();
        } else {
            cacheMap.remove(key);
            return null;
        }
    }

    private boolean isExpired(CacheEntry<V> entry) {
        long currentTime = System.currentTimeMillis();
        return currentTime - entry.getTimestamp() > cacheDurationInMillis;
    }

    private static class CacheEntry<V> {
        private final V value;
        private final long timestamp;

        public CacheEntry(V value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }

        public V getValue() {
            return value;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}
