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
    private Map<UUID, Long> playerCoins = new HashMap<>();

    public CoinManager(TarlaPlugin plugin) {
        this.plugin = plugin;
        this.db = plugin.getDatabaseManager();
        loadAllCoins();
    }

    public void addCoins(Player player, long amount) {
        UUID uuid = player.getUniqueId();
        long currentCoins = getCoins(player);
        long multiplier = plugin.getMultiplierManager().getMultiplier(player);
        long earnedCoins = (long) (amount * multiplier);
        long newTotal = currentCoins + earnedCoins;

        playerCoins.put(uuid, newTotal);
        updateDatabase(uuid, newTotal);
    }

    public void removeCoins(Player player, long amount) {
        UUID uuid = player.getUniqueId();
        long currentCoins = getCoins(player);
        if (currentCoins >= amount) {
            long newTotal = currentCoins - amount;
            playerCoins.put(uuid, newTotal);
            updateDatabase(uuid, newTotal);
        }
    }

    public long getCoins(Player player) {
        UUID uuid = player.getUniqueId();
        return playerCoins.getOrDefault(uuid, 0L);
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
                    playerCoins.put(uuid, coins);
                }
                rs.close();
                plugin.getLogger().info("§aLoaded coins for " + playerCoins.size() + " players!");
            }
        } catch (Exception e) {
            plugin.getLogger().severe("Error loading coins: " + e.getMessage());
        }
    }

    public void saveAllData() {
        playerCoins.forEach((uuid, coins) ->
                db.executeUpdate(
                        "INSERT OR REPLACE INTO player_coins (uuid, coins, last_updated) VALUES (?, ?, ?)",
                        uuid.toString(), coins, System.currentTimeMillis()
                )
        );
        plugin.getLogger().info("§aCoins saved to database!");
    }
}
