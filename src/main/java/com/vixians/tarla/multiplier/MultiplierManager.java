package com.vixians.tarla.multiplier;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.database.DatabaseManager;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MultiplierManager {
    private TarlaPlugin plugin;
    private DatabaseManager db;
    private Map<UUID, String> multiplierTierCache = new HashMap<>();
    private Map<String, Long> tierValues = new HashMap<>();
    private Map<String, Long> tierPrices = new HashMap<>();

    public MultiplierManager(TarlaPlugin plugin) {
        this.plugin = plugin;
        this.db = plugin.getDatabaseManager();
        initializeTiers();
        loadAllMultipliers();
    }

    private void initializeTiers() {
        tierValues.put("default", 1L);
        tierValues.put("bronze", 2L);
        tierValues.put("silver", 3L);
        tierValues.put("gold", 5L);
        tierValues.put("diamond", 10L);
        tierValues.put("emerald", 20L);
        tierValues.put("legendary", 50L);

        tierPrices.put("default", 0L);
        tierPrices.put("bronze", 1000L);
        tierPrices.put("silver", 5000L);
        tierPrices.put("gold", 15000L);
        tierPrices.put("diamond", 50000L);
        tierPrices.put("emerald", 150000L);
        tierPrices.put("legendary", 500000L);
    }

    public long getMultiplier(Player player) {
        UUID uuid = player.getUniqueId();
        String tier = multiplierTierCache.getOrDefault(uuid, "default");
        return tierValues.getOrDefault(tier, 1L);
    }

    public String getMultiplierTier(Player player) {
        UUID uuid = player.getUniqueId();
        return multiplierTierCache.getOrDefault(uuid, "default");
    }

    public void setMultiplier(Player player, String tier) {
        UUID uuid = player.getUniqueId();
        multiplierTierCache.put(uuid, tier);
        updateDatabase(uuid, tier);
    }

    public long getMultiplierPrice(String tier) {
        return tierPrices.getOrDefault(tier, 0L);
    }

    public String getMultiplierDisplayName(String tier) {
        return "&b" + tier.substring(0, 1).toUpperCase() + tier.substring(1) + " &7(" + tierValues.get(tier) + "x)";
    }

    public Map<String, Long> getAllTiers() {
        return new HashMap<>(tierValues);
    }

    private void updateDatabase(UUID uuid, String tier) {
        long multiplierValue = tierValues.getOrDefault(tier, 1L);
        db.executeUpdate(
                "INSERT OR REPLACE INTO player_multipliers (uuid, multiplier_tier, multiplier_value, last_updated) VALUES (?, ?, ?, ?)",
                uuid.toString(), tier, (double) multiplierValue, System.currentTimeMillis()
        );
    }

    private void loadAllMultipliers() {
        try {
            ResultSet rs = db.executeQuery("SELECT uuid, multiplier_tier FROM player_multipliers");
            if (rs != null) {
                while (rs.next()) {
                    UUID uuid = UUID.fromString(rs.getString("uuid"));
                    String tier = rs.getString("multiplier_tier");
                    multiplierTierCache.put(uuid, tier);
                }
                rs.close();
                plugin.getLogger().info("✓ Loaded " + multiplierTierCache.size() + " multiplier records");
            }
        } catch (Exception e) {
            plugin.getLogger().severe("Error loading multipliers: " + e.getMessage());
        }
    }

    public void saveAllData() {
        try {
            for (Map.Entry<UUID, String> entry : multiplierTierCache.entrySet()) {
                updateDatabase(entry.getKey(), entry.getValue());
            }
            plugin.getLogger().info("✓ Saved " + multiplierTierCache.size() + " multiplier records");
        } catch (Exception e) {
            plugin.getLogger().severe("Error saving multipliers: " + e.getMessage());
        }
    }
}
