package com.vixians.tarla.farm;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.database.DatabaseManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FarmManager {
    private TarlaPlugin plugin;
    private DatabaseManager db;
    private Map<String, Long> cropBreakTimes = new HashMap<>();

    public FarmManager(TarlaPlugin plugin) {
        this.plugin = plugin;
        this.db = plugin.getDatabaseManager();
        loadBreakData();
    }

    public void recordCropBreak(Player player, Location location, String cropType) {
        String key = locationToString(location);
        cropBreakTimes.put(key, System.currentTimeMillis());
        
        long coins = plugin.getConfigManager().getCoinsPerCrop();
        plugin.getCoinManager().addCoins(player, coins);
        
        // Save to database
        db.executeUpdate(
                "INSERT INTO farm_breaks (uuid, location, crop_type, break_time, coins_earned) VALUES (?, ?, ?, ?, ?)",
                player.getUniqueId().toString(), key, cropType, System.currentTimeMillis(), coins
        );
    }

    public boolean shouldRegenerateCrop(Location location) {
        String key = locationToString(location);
        Long breakTime = cropBreakTimes.get(key);
        
        if (breakTime == null) return false;
        
        int regenInterval = plugin.getConfigManager().getAutoRegenInterval();
        return (System.currentTimeMillis() - breakTime) >= (regenInterval * 1000L);
    }

    public void regenerateCrop(Location location) {
        String key = locationToString(location);
        cropBreakTimes.remove(key);
    }

    public String isFarmWorld(String worldName) {
        return worldName.equals(plugin.getConfigManager().getFarmWorld()) ? "true" : "false";
    }

    private String locationToString(Location loc) {
        return loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
    }

    private void loadBreakData() {
        try {
            ResultSet rs = db.executeQuery("SELECT location, break_time FROM farm_breaks ORDER BY break_time DESC LIMIT 1000");
            if (rs != null) {
                while (rs.next()) {
                    String location = rs.getString("location");
                    long breakTime = rs.getLong("break_time");
                    cropBreakTimes.put(location, breakTime);
                }
                rs.close();
            }
        } catch (Exception e) {
            plugin.getLogger().severe("Error loading farm data: " + e.getMessage());
        }
    }

    public void saveAllData() {
        plugin.getLogger().info("§aFarm data saved!");
    }
}
