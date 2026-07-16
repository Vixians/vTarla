package com.vixians.tarla.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CacheManager {
    private static final Map<UUID, Long> COIN_CACHE = new ConcurrentHashMap<>();
    private static final Map<UUID, String> MULTIPLIER_CACHE = new ConcurrentHashMap<>();
    private static final long CACHE_EXPIRY = 300000; // 5 minutes
    private static final Map<UUID, Long> CACHE_TIME = new ConcurrentHashMap<>();

    public static void setCoinCache(UUID uuid, long coins) {
        COIN_CACHE.put(uuid, coins);
        CACHE_TIME.put(uuid, System.currentTimeMillis());
    }

    public static long getCoinCache(UUID uuid) {
        if (isCacheExpired(uuid)) {
            COIN_CACHE.remove(uuid);
            return -1;
        }
        return COIN_CACHE.getOrDefault(uuid, -1L);
    }

    public static void setMultiplierCache(UUID uuid, String tier) {
        MULTIPLIER_CACHE.put(uuid, tier);
        CACHE_TIME.put(uuid, System.currentTimeMillis());
    }

    public static String getMultiplierCache(UUID uuid) {
        if (isCacheExpired(uuid)) {
            MULTIPLIER_CACHE.remove(uuid);
            return null;
        }
        return MULTIPLIER_CACHE.get(uuid);
    }

    public static void clearCache(UUID uuid) {
        COIN_CACHE.remove(uuid);
        MULTIPLIER_CACHE.remove(uuid);
        CACHE_TIME.remove(uuid);
    }

    public static void clearAll() {
        COIN_CACHE.clear();
        MULTIPLIER_CACHE.clear();
        CACHE_TIME.clear();
    }

    private static boolean isCacheExpired(UUID uuid) {
        Long cacheTime = CACHE_TIME.get(uuid);
        return cacheTime == null || (System.currentTimeMillis() - cacheTime) > CACHE_EXPIRY;
    }
}
