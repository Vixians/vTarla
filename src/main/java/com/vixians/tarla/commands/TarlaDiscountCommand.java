package com.vixians.tarla.commands;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TarlaDiscountCommand implements CommandExecutor {
    private TarlaPlugin plugin;

    public TarlaDiscountCommand(TarlaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("tarla.admin.discount")) {
            sender.sendMessage("You don't have permission!");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("Usage: /taladiscount <percentage>");
            return true;
        }

        try {
            int percentage = Integer.parseInt(args[0]);
            plugin.getDiscountManager().setDiscount(percentage);
            sender.sendMessage(MessageUtil.colorize("&aDiscount set to &e" + percentage + "%"));
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid number!");
        }

        return true;
    }
}
