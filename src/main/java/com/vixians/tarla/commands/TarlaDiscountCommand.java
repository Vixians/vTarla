package com.vixians.tarla.commands;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TarlaDiscountCommand implements CommandExecutor {
    private TarlaPlugin plugin;

    public TarlaDiscountCommand(TarlaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("tarla.admin.discount")) {
            sender.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix() + "&cYou don't have permission!"));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix() + "&cUsage: /taladiscount [set <amount>|remove]"));
            return true;
        }

        String subCommand = args[0].toLowerCase();

        if (subCommand.equals("set") && args.length > 1) {
            try {
                int discount = Integer.parseInt(args[1]);
                plugin.getDiscountManager().setDiscount(discount, sender.getName());
                sender.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix() + "&aDiscount set to &6" + discount + "%"));
            } catch (NumberFormatException e) {
                sender.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix() + "&cInvalid number!"));
            }
        } else if (subCommand.equals("remove")) {
            plugin.getDiscountManager().removeDiscount(sender.getName());
            sender.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix() + "&aDiscount removed!"));
        }
        return true;
    }
}
