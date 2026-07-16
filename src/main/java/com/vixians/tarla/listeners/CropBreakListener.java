package com.vixians.tarla.listeners;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.utils.MessageUtil;
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
        String worldName = player.getWorld().getName();

        // Check if in farm world
        if (!worldName.equals(plugin.getConfigManager().getFarmWorld())) {
            return;
        }

        String cropType = event.getBlock().getType().name();

        // Check if it's a crop
        if (!isCrop(cropType)) {
            return;
        }

        long coinsPerCrop = plugin.getConfigManager().getCoinsPerCrop();
        long multiplier = plugin.getMultiplierManager().getMultiplier(player);
        long finalCoins = coinsPerCrop * multiplier;
        
        int discount = plugin.getDiscountManager().getCurrentDiscount();
        if (discount > 0) {
            finalCoins = (long) (finalCoins * (1 - discount / 100.0));
        }

        plugin.getCoinManager().addCoins(player, finalCoins);
        plugin.getFarmManager().recordCropBreak(player, event.getBlock().getLocation(), cropType);

        String message = plugin.getConfigManager().getHarvestMessage()
                .replace("{coins}", String.valueOf(finalCoins))
                .replace("{crop}", cropType.toLowerCase());
        player.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix() + message));
    }

    private boolean isCrop(String material) {
        return material.contains("WHEAT") || material.contains("CARROT") ||
               material.contains("POTATO") || material.contains("BEETROOT") ||
               material.contains("NETHER_WART") || material.contains("COCOA");
    }
}
