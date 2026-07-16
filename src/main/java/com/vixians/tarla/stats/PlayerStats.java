package com.vixians.tarla.stats;

import com.vixians.tarla.TarlaPlugin;
import org.bukkit.entity.Player;

public class PlayerStats {
    private TarlaPlugin plugin;
    private Player player;

    public PlayerStats(TarlaPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public long getTotalCoins() {
        return plugin.getCoinManager().getCoins(player);
    }

    public String getMultiplierTier() {
        return plugin.getMultiplierManager().getMultiplierTier(player);
    }

    public long getMultiplierValue() {
        return plugin.getMultiplierManager().getMultiplier(player);
    }

    public int getCurrentDiscount() {
        return plugin.getDiscountManager().getCurrentDiscount();
    }

    public String getStatisticsFormatted() {
        StringBuilder stats = new StringBuilder();
        stats.append("\u00a76========== Player Statistics ==========").append("\n");
        stats.append("\u00a7aPlayer: \u00a76").append(player.getName()).append("\n");
        stats.append("\u00a7aTotal Coins: \u00a76").append(getTotalCoins()).append("\n");
        stats.append("\u00a7aMultiplier Tier: \u00a76").append(getMultiplierTier()).append("\n");
        stats.append("\u00a7aMultiplier Value: \u00a76").append(getMultiplierValue()).append("x\n");
        stats.append("\u00a7aCurrent Discount: \u00a76").append(getCurrentDiscount()).append("%\n");
        stats.append("\u00a76======================================");
        return stats.toString();
    }
}
