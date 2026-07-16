package com.vixians.tarla.utils;

import org.bukkit.Bukkit;

public class ServerUtil {
    public static String getServerVersion() {
        return Bukkit.getServer().getVersion();
    }

    public static int getOnlinePlayerCount() {
        return Bukkit.getOnlinePlayers().size();
    }

    public static double getTPS() {
        return Bukkit.getServer().getTicksPerSecond()[0];
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
