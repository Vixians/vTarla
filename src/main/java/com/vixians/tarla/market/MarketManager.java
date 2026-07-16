package com.vixians.tarla.market;

import com.vixians.tarla.TarlaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MarketManager {
    private TarlaPlugin plugin;
    private Map<String, MarketItem> marketItems = new HashMap<>();

    public MarketManager(TarlaPlugin plugin) {
        this.plugin = plugin;
        loadMarketItems();
    }

    private void loadMarketItems() {
        // Default market items
        marketItems.put("speed_potion", new MarketItem(
                "speed_potion",
                "&bSpeed Potion",
                "POTION",
                500,
                "Gain speed for 5 minutes"
        ));
        
        marketItems.put("haste_potion", new MarketItem(
                "haste_potion",
                "&cHaste Potion",
                "POTION",
                750,
                "Mine faster for 10 minutes"
        ));
        
        marketItems.put("luck_potion", new MarketItem(
                "luck_potion",
                "&eGood Luck Potion",
                "POTION",
                1000,
                "+2x coins for next 10 harvests"
        ));

        plugin.getLogger().info("✓ Loaded " + marketItems.size() + " market items");
    }

    public boolean purchaseItem(Player player, String itemKey) {
        MarketItem item = marketItems.get(itemKey);
        if (item == null) {
            return false;
        }

        if (!plugin.getCoinManager().hasCoins(player, item.getPrice())) {
            return false;
        }

        plugin.getCoinManager().removeCoins(player, item.getPrice());
        // Give item to player
        ItemStack itemStack = new ItemStack(org.bukkit.Material.POTION);
        player.getInventory().addItem(itemStack);

        return true;
    }

    public Map<String, MarketItem> getMarketItems() {
        return marketItems;
    }
}
