package com.vixians.tarla.listeners;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.market.MarketItem;
import com.vixians.tarla.utils.MessageUtil;
import com.vixians.tarla.utils.GUIUtil;
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
        String mainMenuTitle = MessageUtil.colorize("&6vTarla Menu");
        String marketTitle = MessageUtil.colorize(plugin.getConfigManager().getMarketTitle());
        String multiplierTitle = MessageUtil.colorize("&6Multiplier Menu");

        if (title.equals(mainMenuTitle)) {
            event.setCancelled(true);
            handleMainMenuClick(player, clicked);
        } else if (title.equals(marketTitle)) {
            event.setCancelled(true);
            handleMarketClick(player, clicked);
        } else if (title.equals(multiplierTitle)) {
            event.setCancelled(true);
            handleMultiplierClick(player, clicked);
        }
    }

    private void handleMainMenuClick(Player player, ItemStack clicked) {
        if (!clicked.hasItemMeta()) return;
        
        String displayName = clicked.getItemMeta().getDisplayName();
        
        if (displayName.contains("Market")) {
            GUIUtil.openMarketGUI(player);
        } else if (displayName.contains("Multiplier")) {
            GUIUtil.openMultiplierGUI(player);
        } else if (displayName.contains("Statistics")) {
            showStatistics(player);
        } else if (displayName.contains("Help")) {
            showHelp(player);
        }
    }

    private void handleMarketClick(Player player, ItemStack clicked) {
        if (!clicked.hasItemMeta()) return;
        
        String displayName = clicked.getItemMeta().getDisplayName();
        
        for (var item : plugin.getMarketManager().getMarketItems().values()) {
            if (MessageUtil.colorize(item.getName()).equals(displayName)) {
                boolean success = plugin.getMarketManager().purchaseItem(player, item.getKey());
                if (success) {
                    player.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix() + "&aItem purchased!"));
                    GUIUtil.openMarketGUI(player);
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
                
                player.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix() + "&aMultiplier upgraded!"));
                GUIUtil.openMultiplierGUI(player);
                break;
            }
        }
    }

    private void showStatistics(Player player) {
        String prefix = MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix());
        player.sendMessage(prefix + "&6===== Your Statistics =====");
        player.sendMessage(prefix + "&aTotal Coins: &6" + plugin.getCoinManager().getCoins(player));
        player.sendMessage(prefix + "&aMultiplier: &6" + plugin.getMultiplierManager().getMultiplier(player) + "x");
        player.sendMessage(prefix + "&aDiscount: &6" + plugin.getDiscountManager().getCurrentDiscount() + "%");
        player.sendMessage(prefix + "&6=========================");
    }

    private void showHelp(Player player) {
        String prefix = MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix());
        player.sendMessage(prefix + "&6===== vTarla Help =====");
        player.sendMessage(prefix + "&a/tarla &7- Open main menu");
        player.sendMessage(prefix + "&a/tarla market &7- Open market");
        player.sendMessage(prefix + "&a/tarla multiplier &7- Open multiplier");
        player.sendMessage(prefix + "&a/tarla stats &7- View statistics");
        player.sendMessage(prefix + "&6====== Good Farming! =====");
    }
}
