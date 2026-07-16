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
        this.inventory = Bukkit.createInventory(null, 45, title);

        // Add market items
        int slot = 0;
        for (var item : plugin.getMarketManager().getMarketItems().values()) {
            if (slot < 36) {
                ItemStack displayItem = createDisplayItem(item);
                inventory.setItem(slot++, displayItem);
            }
        }

        // Add decorative items
        ItemStack glass = createGlassPane();
        for (int i = 36; i < 45; i++) {
            inventory.setItem(i, glass);
        }

        player.openInventory(inventory);
    }

    private ItemStack createDisplayItem(MarketItem item) {
        try {
            ItemStack stack = new ItemStack(Material.valueOf(item.getMaterial()), item.getAmount());
            ItemMeta meta = stack.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(MessageUtil.colorize(item.getName()));
                
                List<String> lore = new ArrayList<>();
                int discount = plugin.getDiscountManager().getCurrentDiscount();
                long finalPrice = Math.round(item.getPrice() * (100 - discount) / 100.0);
                
                lore.add(MessageUtil.colorize("&7"));
                lore.add(MessageUtil.colorize("&aPrice: &6" + finalPrice + " Tarla Coins"));
                if (discount > 0) {
                    lore.add(MessageUtil.colorize("&c-" + discount + "% Discount!"));
                }
                lore.add(MessageUtil.colorize("&7"));
                lore.add(MessageUtil.colorize("&aLeft-click to purchase!"));
                
                meta.setLore(lore);
                stack.setItemMeta(meta);
            }
            return stack;
        } catch (Exception e) {
            return new ItemStack(Material.STONE);
        }
    }

    private ItemStack createGlassPane() {
        ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = glass.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(MessageUtil.colorize("&8"));
            glass.setItemMeta(meta);
        }
        return glass;
    }
}
