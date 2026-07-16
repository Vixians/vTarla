package com.vixians.tarla.coin;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.database.DatabaseManager;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CoinManager {
    private TarlaPlugin plugin;
    private DatabaseManager db;
    private Map<UUID, Long> coinCache = new HashMap<>();

    public CoinManager(TarlaPlugin plugin) {
        this.plugin = plugin;
        this.db = plugin.getDatabaseManager();
        loadAllCoins();
    }

    public long getCoins(Player player) {
        UUID uuid = player.getUniqueId();
        return coinCache.getOrDefault(uuid, 0L);
    }

    public void setCoins(Player player, long amount) {
        UUID uuid = player.getUniqueId();
        coinCache.put(uuid, Math.max(0, amount));
        updateDatabase(uuid, amount);
    }

    public void addCoins(Player player, long amount) {
        setCoins(player, getCoins(player) + amount);
    }

    public void removeCoins(Player player, long amount) {
        setCoins(player, getCoins(player) - amount);
    }

    public boolean hasCoins(Player player, long amount) {
        return getCoins(player) >= amount;
    }

    private void updateDatabase(UUID uuid, long coins) {
        db.executeUpdate(
                "INSERT OR REPLACE INTO player_coins (uuid, coins, last_updated) VALUES (?, ?, ?)",
                uuid.toString(), coins, System.currentTimeMillis()
        );
    }

    private void loadAllCoins() {
        try {
            ResultSet rs = db.executeQuery("SELECT uuid, coins FROM player_coins");
            if (rs != null) {
                while (rs.next()) {
                    UUID uuid = UUID.fromString(rs.getString("uuid"));
                    long coins = rs.getLong("coins");
                    coinCache.put(uuid, coins);
                }
                rs.close();
                plugin.getLogger().info("✓ Loaded " + coinCache.size() + " player coin records");
            }
        } catch (Exception e) {
            plugin.getLogger().severe("Error loading coins: " + e.getMessage());
        }
    }

    public void saveAllData() {
        try {
            for (Map.Entry<UUID, Long> entry : coinCache.entrySet()) {
                updateDatabase(entry.getKey(), entry.getValue());
            }
            plugin.getLogger().info("✓ Saved " + coinCache.size() + " coin records");
        } catch (Exception e) {
            plugin.getLogger().severe("Error saving coins: " + e.getMessage());
        }
    }
}
