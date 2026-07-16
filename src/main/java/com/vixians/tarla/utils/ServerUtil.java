package com.vixians.tarla.utils;

import org.bukkit.Bukkit;

public class ServerUtil {
    public static String getServerVersion() {
        return Bukkit.getServer().getVersion();
    }

    public static int getOnlinePlayerCount() {
        return Bukkit.getOnlinePlayers().size();
    }

    @SuppressWarnings("deprecation")
    public static double getTPS() {
        try {
            return Bukkit.getServer().getTicksPerSecond()[0];
        } catch (Exception e) {
            return -1.0; // Fallback if method not available
        }
    }

    public static boolean isOnlineMode() {
        return Bukkit.getServer().getOnlineMode();
    }

    public static long getMaxMemory() {
        return Runtime.getRuntime().maxMemory() / 1024 / 1024; // MB
    }

    public static long getUsedMemory() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024; // MB
    }
}
