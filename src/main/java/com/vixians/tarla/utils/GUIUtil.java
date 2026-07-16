package com.vixians.tarla.utils;

import com.vixians.tarla.TarlaPlugin;
import org.bukkit.entity.Player;

public class GUIUtil {
    private static TarlaPlugin plugin;

    public GUIUtil(TarlaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void openMarketGUI(Player player) {
        plugin.getTaskManager().runSync(() -> {
            com.vixians.tarla.market.MarketGUI gui = new com.vixians.tarla.market.MarketGUI(plugin, player);
            gui.open();
        });
    }

    public static void openMultiplierGUI(Player player) {
        plugin.getTaskManager().runSync(() -> {
            com.vixians.tarla.market.MultiplierGUI gui = new com.vixians.tarla.market.MultiplierGUI(plugin, player);
            gui.open();
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
