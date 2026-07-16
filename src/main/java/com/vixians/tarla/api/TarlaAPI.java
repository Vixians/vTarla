package com.vixians.tarla.api;

import com.vixians.tarla.TarlaPlugin;
import org.bukkit.entity.Player;

/**
 * vTarla Plugin API
 * External plugins can use this to interact with vTarla
 */
public class TarlaAPI {
    private static TarlaPlugin plugin;

    public TarlaAPI(TarlaPlugin plugin) {
        TarlaAPI.plugin = plugin;
    }

    public static long getPlayerCoins(Player player) {
        return plugin.getCoinManager().getCoins(player);
    }

    public static void addPlayerCoins(Player player, long amount) {
        plugin.getCoinManager().addCoins(player, amount);
    }

    public static void removePlayerCoins(Player player, long amount) {
        plugin.getCoinManager().removeCoins(player, amount);
    }

    public static boolean hasCoins(Player player, long amount) {
        return plugin.getCoinManager().hasCoins(player, amount);
    }

    public static long getPlayerMultiplier(Player player) {
        return plugin.getMultiplierManager().getMultiplier(player);
    }

    public static String getPlayerMultiplierTier(Player player) {
        return plugin.getMultiplierManager().getMultiplierTier(player);
    }

    public static boolean setPlayerMultiplier(Player player, String tier) {
        return plugin.getMultiplierManager().setMultiplier(player, tier);
    }

    public static int getCurrentDiscount() {
        return plugin.getDiscountManager().getCurrentDiscount();
    }

    public static void setDiscount(int discount, String setBy) {
        plugin.getDiscountManager().setDiscount(discount, setBy);
    }

    public static String getFarmWorld() {
        return plugin.getConfigManager().getFarmWorld();
    }
}
