package com.vixians.tarla.commands;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.utils.MessageUtil;
import com.vixians.tarla.utils.GUIUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

        GUIUtil.openMarketGUI(player);
        return true;
    }
}
