package com.vixians.tarla.menu;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {
    private TarlaPlugin plugin;
    private Player player;
    private Inventory inventory;

    public MainMenu(TarlaPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public void open() {
        this.inventory = Bukkit.createInventory(null, 27, MessageUtil.colorize("&6vTarla Menu"));

        // Player Head with stats
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        if (skullMeta != null) {
            skullMeta.setOwningPlayer(player);
            long coins = plugin.getCoinManager().getCoins(player);
            String tier = plugin.getMultiplierManager().getMultiplierTier(player);
            long multiplier = plugin.getMultiplierManager().getMultiplier(player);
            
            skullMeta.setDisplayName(MessageUtil.colorize("&6" + player.getName()));
            List<String> lore = new ArrayList<>();
            lore.add(MessageUtil.colorize("&7"));
            lore.add(MessageUtil.colorize("&aCoins: &6" + coins));
            lore.add(MessageUtil.colorize("&aTier: &6" + tier));
            lore.add(MessageUtil.colorize("&aMultiplier: &6" + multiplier + "x"));
            lore.add(MessageUtil.colorize("&7"));
            skullMeta.setLore(lore);
            playerHead.setItemMeta(skullMeta);
        }
        inventory.setItem(4, playerHead);

        // Market
        ItemStack market = createMenuItem(
            Material.EMERALD,
            "&6Market",
            "&7Browse and purchase items",
            "&aClick to open!"
        );
        inventory.setItem(11, market);

        // Multiplier
        ItemStack multiplier = createMenuItem(
            Material.DIAMOND,
            "&bMultiplier",
            "&7Upgrade your earnings",
            "&aClick to open!"
        );
        inventory.setItem(13, multiplier);

        // Statistics
        ItemStack stats = createMenuItem(
            Material.BOOK,
            "&eStatistics",
            "&7View detailed stats",
            "&aClick to view!"
        );
        inventory.setItem(15, stats);

        // Help
        ItemStack help = createMenuItem(
            Material.REDSTONE_LAMP,
            "&cHelp",
            "&7Learn about vTarla",
            "&aClick for help!"
        );
        inventory.setItem(22, help);

        player.openInventory(inventory);
    }

    private ItemStack createMenuItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(MessageUtil.colorize(name));
            List<String> loreList = new ArrayList<>();
            loreList.add(MessageUtil.colorize("&7"));
            for (String loreLine : lore) {
                loreList.add(MessageUtil.colorize(loreLine));
            }
            loreList.add(MessageUtil.colorize("&7"));
            meta.setLore(loreList);
            item.setItemMeta(meta);
        }
        return item;
    }
}
