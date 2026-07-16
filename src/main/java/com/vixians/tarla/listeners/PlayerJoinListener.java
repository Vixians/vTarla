package com.vixians.tarla.listeners;

import com.vixians.tarla.TarlaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private TarlaPlugin plugin;

    public PlayerJoinListener(TarlaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // Load player data
        plugin.getTaskManager().runAsync(() -> {
            // Coin data is automatically loaded
            long coins = plugin.getCoinManager().getCoins(player);
            plugin.getLogger().info("Player " + player.getName() + " joined with " + coins + " coins");
        });
    }
}
