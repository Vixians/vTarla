package com.vixians.tarla.commands;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AdminCommand implements CommandExecutor {
    private TarlaPlugin plugin;

    public AdminCommand(TarlaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("tarla.admin.*")) {
            sender.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix() + "&cNo permission!"));
            return true;
        }

        if (args.length == 0) {
            sendAdminHelp(sender);
            return true;
        }

        String subCmd = args[0].toLowerCase();

        switch (subCmd) {
            case "backup":
                handleBackup(sender);
                break;
            case "stats":
                handleGlobalStats(sender);
                break;
            case "reload":
                handleReload(sender);
                break;
            default:
                sendAdminHelp(sender);
        }

        return true;
    }

    private void handleBackup(CommandSender sender) {
        plugin.getTaskManager().runAsync(() -> {
            // Backup logic here
            sender.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix() + "&aBackup created!"));
        });
    }

    private void handleGlobalStats(CommandSender sender) {
        long totalPlayers = plugin.getDatabaseManager().getTotalPlayers();
        long totalCoins = plugin.getDatabaseManager().getTotalCoins();
        
        sender.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix() + "&6=== Global Stats ==="));
        sender.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix() + "&aTotal Players: &6" + totalPlayers));
        sender.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix() + "&aTotal Coins: &6" + totalCoins));
    }

    private void handleReload(CommandSender sender) {
        plugin.getConfigManager().reloadConfig();
        sender.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix() + "&aPlugin reloaded!"));
    }

    private void sendAdminHelp(CommandSender sender) {
        String prefix = MessageUtil.colorize(plugin.getConfigManager().getMessagePrefix());
        sender.sendMessage(prefix + "&6=== Admin Commands ===");
        sender.sendMessage(prefix + "&a/taladmin backup &7- Create database backup");
        sender.sendMessage(prefix + "&a/taladmin stats &7- Show global statistics");
        sender.sendMessage(prefix + "&a/taladmin reload &7- Reload configuration");
    }
}
