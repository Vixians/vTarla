package com.vixians.tarla.utils;

import com.vixians.tarla.TarlaPlugin;
import org.bukkit.entity.Player;

public class GUIUtil {
    private static TarlaPlugin plugin;

    public GUIUtil(TarlaPlugin tarlaPlugin) {
        plugin = tarlaPlugin;
    }

    public static void setPlugin(TarlaPlugin tarlaPlugin) {
        plugin = tarlaPlugin;
    }

    public static void openMainMenu(Player player) {
        if (plugin == null) {
            player.sendMessage("\u00a7cPlugin not initialized!");
            return;
        }
        new com.vixians.tarla.menu.MainMenu(plugin, player).open();
    }

    public static void openMarketGUI(Player player) {
        if (plugin == null) {
            player.sendMessage("\u00a7cPlugin not initialized!");
            return;
        }
        plugin.getTaskManager().runSync(() -> {
            new com.vixians.tarla.market.MarketGUI(plugin, player).open();
        });
    }

    public static void openMultiplierGUI(Player player) {
        if (plugin == null) {
            player.sendMessage("\u00a7cPlugin not initialized!");
            return;
        }
        plugin.getTaskManager().runSync(() -> {
            new com.vixians.tarla.market.MultiplierGUI(plugin, player).open();
        });
    }

    public static String formatTimeRemaining(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) return days + "d";
        if (hours > 0) return hours + "h";
        if (minutes > 0) return minutes + "m";
        return seconds + "s";
    }
}
