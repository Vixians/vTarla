package com.vixians.tarla.multiplier;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.database.DatabaseManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MultiplierManager {
    private TarlaPlugin plugin;
    private DatabaseManager db;
    private Map<UUID, String> playerMultipliers = new HashMap<>();
    private Map<String, Double> multiplierTiers = new HashMap<>();
    private Map<String, String> multiplierPermissions = new HashMap<>();

    public MultiplierManager(TarlaPlugin plugin) {
        this.plugin = plugin;
        this.db = plugin.getDatabaseManager();
        loadMultiplierTiers();
        loadPlayerMultipliers();
    }

    private void loadMultiplierTiers() {
        ConfigurationSection tiers = plugin.getConfigManager().getConfig()
                .getConfigurationSection("multiplier.tiers");
        
        if (tiers != null) {
            for (String tier : tiers.getKeys(false)) {
                double multiplier = tiers.getDouble(tier + ".multiplier", 1.0);
                String permission = tiers.getString(tier + ".permission", "");
                multiplierTiers.put(tier, multiplier);
                multiplierPermissions.put(tier, permission);
            }
        }
    }

    private void loadPlayerMultipliers() {
        try {
            ResultSet rs = db.executeQuery("SELECT uuid, multiplier_tier FROM player_multipliers");
            if (rs != null) {
                while (rs.next()) {
                    UUID uuid = UUID.fromString(rs.getString("uuid"));
                    String tier = rs.getString("multiplier_tier");
                    playerMultipliers.put(uuid, tier);
                }
                rs.close();
            }
        } catch (Exception e) {
            plugin.getLogger().severe("Error loading multipliers: " + e.getMessage());
        }
    }

    public long getMultiplier(Player player) {
        UUID uuid = player.getUniqueId();
        String tier = playerMultipliers.getOrDefault(uuid, "default");
        Double mult = multiplierTiers.get(tier);
        return mult != null ? mult.longValue() : 1L;
    }

    public String getMultiplierTier(Player player) {
        return playerMultipliers.getOrDefault(player.getUniqueId(), "default");
    }

    public boolean setMultiplier(Player player, String tier) {
        String permission = multiplierPermissions.get(tier);
        
        if (permission != null && !player.hasPermission(permission)) {
            return false;
        }

        UUID uuid = player.getUniqueId();
        playerMultipliers.put(uuid, tier);
        Double multiplier = multiplierTiers.getOrDefault(tier, 1.0);
        
        db.executeUpdate(
                "INSERT OR REPLACE INTO player_multipliers (uuid, multiplier_tier, multiplier_value, last_updated) VALUES (?, ?, ?, ?)",
                uuid.toString(), tier, multiplier, System.currentTimeMillis()
        );
        return true;
    }

    public long getMultiplierPrice(String tier) {
        return plugin.getConfigManager().getConfig()
                .getLong("multiplier.tiers." + tier + ".price", 0);
    }

    public String getMultiplierDisplayName(String tier) {
        return plugin.getConfigManager().getConfig()
                .getString("multiplier.tiers." + tier + ".display-name", tier);
    }

    public Map<String, Double> getAllTiers() {
        return new HashMap<>(multiplierTiers);
    }

    public void saveAllData() {
        playerMultipliers.forEach((uuid, tier) -> {
            Double mult = multiplierTiers.get(tier);
            db.executeUpdate(
                    "INSERT OR REPLACE INTO player_multipliers (uuid, multiplier_tier, multiplier_value, last_updated) VALUES (?, ?, ?, ?)",
                    uuid.toString(), tier, mult != null ? mult : 1.0, System.currentTimeMillis()
            );
        });
        plugin.getLogger().info("§aMultipliers saved to database!");
    }
}
