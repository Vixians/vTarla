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
        this.inventory = Bukkit.createInventory(null, 36, MessageUtil.colorize("&6Multiplier Menu"));

        // Add current multiplier info
        ItemStack currentInfo = createCurrentMultiplierItem();
        inventory.setItem(4, currentInfo);

        // Add available multipliers
        int slot = 9;
        for (var tier : plugin.getMultiplierManager().getAllTiers().entrySet()) {
            if (slot < 27) {
                ItemStack tierItem = createTierItem(tier.getKey(), tier.getValue());
                inventory.setItem(slot, tierItem);
                slot += 2;
            }
        }

        player.openInventory(inventory);
    }

    private ItemStack createCurrentMultiplierItem() {
        ItemStack stack = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            String currentTier = plugin.getMultiplierManager().getMultiplierTier(player);
            long multiplier = plugin.getMultiplierManager().getMultiplier(player);
            
            meta.setDisplayName(MessageUtil.colorize("&6Your Multiplier"));
            List<String> lore = new ArrayList<>();
            lore.add(MessageUtil.colorize("&7Tier: &a" + currentTier));
            lore.add(MessageUtil.colorize("&7Multiplier: &a" + multiplier + "x"));
            meta.setLore(lore);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    private ItemStack createTierItem(String tier, double multiplier) {
        ItemStack stack = new ItemStack(Material.DIAMOND);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(MessageUtil.colorize(plugin.getMultiplierManager().getMultiplierDisplayName(tier)));
            
            List<String> lore = new ArrayList<>();
            long price = plugin.getMultiplierManager().getMultiplierPrice(tier);
            long playerCoins = plugin.getCoinManager().getCoins(player);
            
            lore.add(MessageUtil.colorize("&7Multiplier: &a" + multiplier + "x"));
            lore.add(MessageUtil.colorize("&7Price: &6" + price + " coins"));
            lore.add(MessageUtil.colorize("&7Your coins: &a" + playerCoins));
            lore.add(MessageUtil.colorize("&7"));
            
            if (playerCoins >= price) {
                lore.add(MessageUtil.colorize("&aClick to purchase!"));
            } else {
                lore.add(MessageUtil.colorize("&cInsufficient coins!"));
            }
            
            meta.setLore(lore);
            stack.setItemMeta(meta);
        }
        return stack;
    }
}
