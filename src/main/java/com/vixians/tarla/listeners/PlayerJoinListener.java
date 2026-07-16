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
        
        // Initialize player data
        if (plugin.getCoinManager().getCoins(player) == 0 && !player.hasPlayedBefore()) {
            plugin.getCoinManager().addCoins(player, 100); // Starting bonus
        }
        
        // Set default multiplier if not set
        if (plugin.getMultiplierManager().getMultiplier(player) == 0) {
            plugin.getMultiplierManager().setMultiplier(player, "default");
        }
    }
}
