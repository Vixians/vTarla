package com.vixians.tarla.farm;

public class FarmData {
    private String playerUUID;
    private String cropType;
    private long breakTime;
    private long coinsEarned;
    private String location;

    public FarmData(String playerUUID, String cropType, long breakTime, long coinsEarned, String location) {
        this.playerUUID = playerUUID;
        this.cropType = cropType;
        this.breakTime = breakTime;
        this.coinsEarned = coinsEarned;
        this.location = location;
    }

    // Getters and Setters
    public String getPlayerUUID() { return playerUUID; }
    public String getCropType() { return cropType; }
    public long getBreakTime() { return breakTime; }
    public long getCoinsEarned() { return coinsEarned; }
    public String getLocation() { return location; }
}
