package com.vixians.tarla.listeners;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.utils.MessageUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class CropBreakListener implements Listener {
    private TarlaPlugin plugin;

    public CropBreakListener(TarlaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCropBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        String farmWorld = plugin.getConfigManager().getFarmWorld();
        if (!block.getWorld().getName().equals(farmWorld)) {
            return; // Not in farm world
        }

        String blockType = block.getType().toString();
        boolean isCrop = isCrop(blockType);

        if (!isCrop) {
            return;
        }

        // Record the break
        plugin.getFarmManager().recordCropBreak(player, block.getLocation(), blockType);

        // Send action bar message
        long coins = plugin.getConfigManager().getCoinsPerCrop();
        long multiplier = plugin.getMultiplierManager().getMultiplier(player);
        long earnedCoins = coins * multiplier;
        
        String message = plugin.getConfigManager().getCoinEarnMessage()
                .replace("{coins}", String.valueOf(earnedCoins))
                .replace("{coin_name}", plugin.getConfigManager().getCoinDisplayName());
        
        player.sendActionBar(MessageUtil.colorize(message));
    }

    private boolean isCrop(String blockType) {
        return blockType.contains("WHEAT") || blockType.contains("CARROT") || 
               blockType.contains("POTATO") || blockType.contains("BEETROOT") ||
               blockType.contains("NETHER_WART") || blockType.contains("COCOA");
    }
}
