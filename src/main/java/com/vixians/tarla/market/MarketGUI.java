package com.vixians.tarla.market;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MarketGUI {
    private TarlaPlugin plugin;
    private Player player;
    private Inventory inventory;

    public MarketGUI(TarlaPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public void open() {
        String title = MessageUtil.colorize(plugin.getConfigManager().getMarketTitle());
        this.inventory = Bukkit.createInventory(null, 54, title);

        int slot = 0;
        for (MarketItem item : plugin.getMarketManager().getMarketItems().values()) {
            if (slot < 54) {
                inventory.setItem(slot, createMarketItem(item));
                slot++;
            }
        }

        player.openInventory(inventory);
    }

    private ItemStack createMarketItem(MarketItem item) {
        ItemStack itemStack = new ItemStack(getMaterialFromString(item.getMaterial()));
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(MessageUtil.colorize(item.getName()));
            List<String> lore = new ArrayList<>();
            lore.add(MessageUtil.colorize("&7"));
            lore.add(MessageUtil.colorize("&ePrice: &6" + item.getPrice()));
            lore.add(MessageUtil.colorize("&eDescription: &7" + item.getDescription()));
            lore.add(MessageUtil.colorize("&7"));
            lore.add(MessageUtil.colorize("&aClick to purchase!"));
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    private Material getMaterialFromString(String name) {
        try {
            return Material.valueOf(name.toUpperCase());
        } catch (Exception e) {
            return Material.DIAMOND;
        }
    }
}
