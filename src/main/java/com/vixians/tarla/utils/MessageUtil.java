package com.vixians.tarla.utils;

import net.md_5.bungee.api.ChatColor;

public class MessageUtil {
    public static String colorize(String message) {
        if (message == null) return "";
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String stripColor(String message) {
        if (message == null) return "";
        return ChatColor.stripColor(message);
    }
}
