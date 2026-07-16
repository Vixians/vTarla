package com.vixians.tarla.commands;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.Bukkit;

public class TarlaMarketCommand implements CommandExecutor {
    private TarlaPlugin plugin;

    public TarlaMarketCommand(TarlaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;
        
        if (!player.hasPermission("tarla.market")) {
            player.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix() + "&cYou don't have permission!"));
            return true;
        }

        openMarket(player);
        return true;
    }

    private void openMarket(Player player) {
        String title = MessageUtil.colorize(plugin.getConfigManager().getMarketTitle());
        Inventory market = Bukkit.createInventory(null, 36, title);
        
        int slot = 0;
        for (var item : plugin.getMarketManager().getMarketItems().values()) {
            if (slot < 36) {
                market.setItem(slot++, item.toItemStack());
            }
        }
        
        player.openInventory(market);
    }
}
