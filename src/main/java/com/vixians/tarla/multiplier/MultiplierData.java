package com.vixians.tarla.multiplier;

import java.util.UUID;

public class MultiplierData {
    private UUID playerUUID;
    private String multiplierTier;
    private double multiplierValue;
    private long lastUpdated;

    public MultiplierData(UUID playerUUID, String multiplierTier, double multiplierValue, long lastUpdated) {
        this.playerUUID = playerUUID;
        this.multiplierTier = multiplierTier;
        this.multiplierValue = multiplierValue;
        this.lastUpdated = lastUpdated;
    }

    // Getters and Setters
    public UUID getPlayerUUID() { return playerUUID; }
    public String getMultiplierTier() { return multiplierTier; }
    public void setMultiplierTier(String tier) { this.multiplierTier = tier; }
    public double getMultiplierValue() { return multiplierValue; }
    public void setMultiplierValue(double value) { this.multiplierValue = value; }
    public long getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(long lastUpdated) { this.lastUpdated = lastUpdated; }
}
