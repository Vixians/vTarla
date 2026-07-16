package com.vixians.tarla.commands;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TarlaCommand implements CommandExecutor {
    private TarlaPlugin plugin;

    public TarlaCommand(TarlaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sendHelp(player);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "help":
                sendHelp(player);
                break;
            case "stats":
                sendStats(player);
                break;
            case "profile":
                sendProfile(player);
                break;
            case "market":
                plugin.getTaskManager().runAsync(() -> {
                    // Open market
                });
                break;
            case "multiplier":
                plugin.getTaskManager().runAsync(() -> {
                    // Open multiplier GUI
                });
                break;
            case "reload":
                if (!player.hasPermission("tarla.admin.reload")) {
                    player.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix() + "&cYou don't have permission!"));
                    return true;
                }
                reloadPlugin();
                break;
            default:
                sendHelp(player);
        }
        return true;
    }

    private void sendHelp(Player player) {
        String prefix = MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix());
        player.sendMessage(prefix + "§6========== vTarla Help ==========");
        player.sendMessage(prefix + "§a/tarla stats §7- Show your statistics");
        player.sendMessage(prefix + "§a/tarla profile §7- Show your profile");
        player.sendMessage(prefix + "§a/tarla market §7- Open market");
        player.sendMessage(prefix + "§a/tarla multiplier §7- Open multiplier menu");
        if (player.hasPermission("tarla.admin.reload")) {
            player.sendMessage(prefix + "§a/tarla reload §7- Reload plugin");
        }
        player.sendMessage(prefix + "§6================================");
    }

    private void sendStats(Player player) {
        long coins = plugin.getCoinManager().getCoins(player);
        long multiplier = plugin.getMultiplierManager().getMultiplier(player);
        int discount = plugin.getDiscountManager().getCurrentDiscount();
        
        String prefix = MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix());
        player.sendMessage(prefix + "§6========== Your Statistics ==========");
        player.sendMessage(prefix + "§aCoins: §6" + coins);
        player.sendMessage(prefix + "§aMultiplier: §6" + multiplier + "x");
        player.sendMessage(prefix + "§aDiscount: §6" + discount + "%");
        player.sendMessage(prefix + "§6=====================================");
    }

    private void sendProfile(Player player) {
        sendStats(player);
    }

    private void reloadPlugin() {
        plugin.getConfigManager().reloadConfig();
        plugin.getLogger().info("§aPlugin reloaded!");
    }
}
