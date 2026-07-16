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

public class MultiplierGUI {
    private TarlaPlugin plugin;
    private Player player;
    private Inventory inventory;

    public MultiplierGUI(TarlaPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public void open() {
        this.inventory = Bukkit.createInventory(null, 27, MessageUtil.colorize("&6Multiplier Menu"));

        int slot = 0;
        for (String tier : plugin.getMultiplierManager().getAllTiers().keySet()) {
            if (slot < 27) {
                ItemStack item = createTierItem(tier);
                inventory.setItem(slot, item);
                slot += 2;
            }
        }

        player.openInventory(inventory);
    }

    private ItemStack createTierItem(String tier) {
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(MessageUtil.colorize(plugin.getMultiplierManager().getMultiplierDisplayName(tier)));
            List<String> lore = new ArrayList<>();
            lore.add(MessageUtil.colorize("&7"));
            long multiplier = plugin.getMultiplierManager().getAllTiers().get(tier);
            long price = plugin.getMultiplierManager().getMultiplierPrice(tier);
            lore.add(MessageUtil.colorize("&aMultiplier: &6" + multiplier + "x"));
            lore.add(MessageUtil.colorize("&aPrice: &6" + price + " coins"));
            lore.add(MessageUtil.colorize("&7"));
            
            String currentTier = plugin.getMultiplierManager().getMultiplierTier(player);
            if (currentTier.equals(tier)) {
                lore.add(MessageUtil.colorize("&a✓ Current Tier"));
            } else {
                lore.add(MessageUtil.colorize("&aClick to upgrade!"));
            }
            
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }
}
