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
    private SmartCropRegen smartCropRegen;

    public FarmManager(TarlaPlugin plugin) {
        this.plugin = plugin;
        this.db = plugin.getDatabaseManager();
        this.smartCropRegen = new SmartCropRegen(plugin);
        loadBreakData();
    }

    public void recordCropBreak(Player player, Location location, String cropType) {
        String key = locationToString(location);
        smartCropRegen.recordCropBreak(key);
        
        long coins = plugin.getConfigManager().getCoinsPerCrop();
        plugin.getCoinManager().addCoins(player, coins);
        
        // Save to database
        db.executeUpdate(
                "INSERT INTO farm_breaks (uuid, location, crop_type, break_time, coins_earned) VALUES (?, ?, ?, ?, ?)",
                player.getUniqueId().toString(), key, cropType, System.currentTimeMillis(), coins
        );
    }

    public String isFarmWorld(String worldName) {
        return worldName.equals(plugin.getConfigManager().getFarmWorld()) ? "true" : "false";
    }

    private String locationToString(Location loc) {
        return loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
    }

    private void loadBreakData() {
        try {
            ResultSet rs = db.executeQuery("SELECT location FROM farm_breaks ORDER BY break_time DESC LIMIT 100");
            if (rs != null) {
                while (rs.next()) {
                    String location = rs.getString("location");
                    smartCropRegen.recordCropBreak(location);
                }
                rs.close();
                plugin.getLogger().info("✓ Loaded farm break data");
            }
        } catch (Exception e) {
            plugin.getLogger().severe("Error loading farm data: " + e.getMessage());
        }
    }

    public void saveAllData() {
        plugin.getLogger().info("✓ Farm data saved!");
    }
}
