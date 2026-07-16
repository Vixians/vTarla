package com.vixians.tarla.listeners;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.market.MarketItem;
import com.vixians.tarla.utils.MessageUtil;
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
        String marketTitle = MessageUtil.colorize(plugin.getConfigManager().getMarketTitle());
        String multiplierTitle = MessageUtil.colorize("&6Multiplier Menu");

        if (title.equals(marketTitle)) {
            event.setCancelled(true);
            handleMarketClick(player, clicked);
        } else if (title.equals(multiplierTitle)) {
            event.setCancelled(true);
            handleMultiplierClick(player, clicked);
        }
    }

    private void handleMarketClick(Player player, ItemStack clicked) {
        if (!clicked.hasItemMeta()) return;
        
        String displayName = clicked.getItemMeta().getDisplayName();
        
        for (var item : plugin.getMarketManager().getMarketItems().values()) {
            if (MessageUtil.colorize(item.getName()).equals(displayName)) {
                boolean success = plugin.getMarketManager().purchaseItem(player, item.getKey());
                if (success) {
                    String message = plugin.getConfigManager().getMessage("coin-purchase-success")
                            .replace("{item}", item.getName())
                            .replace("{price}", String.valueOf(item.getPrice()));
                    player.sendMessage(MessageUtil.colorize(message));
                }
                break;
            }
        }
    }

    private void handleMultiplierClick(Player player, ItemStack clicked) {
        if (!clicked.hasItemMeta()) return;
        
        String displayName = clicked.getItemMeta().getDisplayName();
        
        for (var tier : plugin.getMultiplierManager().getAllTiers().entrySet()) {
            String tierDisplay = MessageUtil.colorize(plugin.getMultiplierManager().getMultiplierDisplayName(tier.getKey()));
            if (tierDisplay.equals(displayName)) {
                long price = plugin.getMultiplierManager().getMultiplierPrice(tier.getKey());
                
                if (!plugin.getCoinManager().hasCoins(player, price)) {
                    player.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix() + "&cInsufficient coins!"));
                    return;
                }
                
                plugin.getCoinManager().removeCoins(player, price);
                plugin.getMultiplierManager().setMultiplier(player, tier.getKey());
                
                String message = plugin.getConfigManager().getMessagePrefix() + "&aMultiplier changed to &6" + tier.getValue() + "x";
                player.sendMessage(MessageUtil.colorize(message));
                break;
            }
        }
    }
}
