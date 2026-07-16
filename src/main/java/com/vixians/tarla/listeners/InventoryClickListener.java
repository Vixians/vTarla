package com.vixians.tarla.listeners;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.market.MarketItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {
    private TarlaPlugin plugin;

    public InventoryClickListener(TarlaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();

        if (clicked == null || !clicked.hasItemMeta()) {
            return;
        }

        String title = event.getView().getTitle();
        String marketTitle = plugin.getConfigManager().getMarketTitle().replace("&", "§");

        if (title.equals(marketTitle)) {
            event.setCancelled(true);
            
            // Find matching market item
            for (var item : plugin.getMarketManager().getMarketItems().values()) {
                if (item.getName().replace("&", "§").equals(clicked.getItemMeta().getDisplayName())) {
                    plugin.getMarketManager().purchaseItem(player, item.getKey());
                    break;
                }
            }
        }
    }
}
