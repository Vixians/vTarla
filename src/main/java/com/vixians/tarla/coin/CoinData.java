package com.vixians.tarla.coin;

import java.util.UUID;

public class CoinData {
    private UUID playerUUID;
    private long coins;
    private long lastUpdated;

    public CoinData(UUID playerUUID, long coins, long lastUpdated) {
        this.playerUUID = playerUUID;
        this.coins = coins;
        this.lastUpdated = lastUpdated;
    }

    // Getters and Setters
    public UUID getPlayerUUID() { return playerUUID; }
    public long getCoins() { return coins; }
    public void setCoins(long coins) { this.coins = coins; }
    public long getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(long lastUpdated) { this.lastUpdated = lastUpdated; }
}
