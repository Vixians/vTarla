package com.vixians.tarla.commands;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.Bukkit;

public class TarlaMultiplierCommand implements CommandExecutor {
    private TarlaPlugin plugin;

    public TarlaMultiplierCommand(TarlaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;
        
        if (!player.hasPermission("tarla.multiplier")) {
            player.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix() + "&cYou don't have permission!"));
            return true;
        }

        openMultiplierGUI(player);
        return true;
    }

    private void openMultiplierGUI(Player player) {
        String title = "§6Multiplier Menu";
        Inventory gui = Bukkit.createInventory(null, 27, title);
        
        player.openInventory(gui);
    }
}
