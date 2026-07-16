package com.vixians.tarla.utils;

import net.md_5.bungee.api.ChatColor;

public class MessageUtil {
    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String decolorize(String message) {
        return message.replaceAll("§[0-9a-fk-or]", "");
    }

    public static String formatNumber(long number) {
        if (number >= 1_000_000) {
            return String.format("%.1fM", number / 1_000_000.0);
        }
        if (number >= 1_000) {
            return String.format("%.1fK", number / 1_000.0);
        }
        return String.valueOf(number);
    }
}
