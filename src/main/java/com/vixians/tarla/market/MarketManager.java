package com.vixians.tarla.market;

import com.vixians.tarla.TarlaPlugin;
import org.bukkit.configuration.ConfigurationSection;
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
        ConfigurationSection items = plugin.getConfigManager().getConfig()
                .getConfigurationSection("market.items");
        
        if (items != null) {
            for (String itemKey : items.getKeys(false)) {
                String name = items.getString(itemKey + ".name", itemKey);
                String material = items.getString(itemKey + ".material", "STONE");
                long price = items.getLong(itemKey + ".price", 0);
                int amount = items.getInt(itemKey + ".amount", 1);
                String command = items.getString(itemKey + ".command", "none");

                MarketItem item = new MarketItem(itemKey, name, material, price, amount, command);
                marketItems.put(itemKey, item);
            }
        }
    }

    public Map<String, MarketItem> getMarketItems() {
        return new HashMap<>(marketItems);
    }

    public MarketItem getMarketItem(String key) {
        return marketItems.get(key);
    }

    public boolean purchaseItem(Player player, String itemKey) {
        MarketItem item = getMarketItem(itemKey);
        if (item == null) return false;

        long price = item.getPrice();
        int discount = plugin.getDiscountManager().getCurrentDiscount();
        long finalPrice = Math.round(price * (100 - discount) / 100.0);

        if (!plugin.getCoinManager().hasCoins(player, finalPrice)) {
            player.sendMessage(plugin.getConfigManager().getMessage("coin-insufficient"));
            return false;
        }

        plugin.getCoinManager().removeCoins(player, finalPrice);
        
        if (!item.getCommand().equals("none")) {
            String command = item.getCommand().replace("{player}", player.getName());
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
        } else {
            ItemStack itemStack = item.toItemStack();
            player.getInventory().addItem(itemStack);
        }

        return true;
    }
}
