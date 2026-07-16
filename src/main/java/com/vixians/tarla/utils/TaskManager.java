package com.vixians.tarla.utils;

import com.vixians.tarla.TarlaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskManager {
    private TarlaPlugin plugin;
    private BukkitScheduler scheduler;
    private ExecutorService executorService;

    public TaskManager(TarlaPlugin plugin) {
        this.plugin = plugin;
        this.scheduler = plugin.getServer().getScheduler();
        this.executorService = Executors.newFixedThreadPool(4);
    }

    public void runAsync(Runnable runnable) {
        executorService.execute(runnable);
    }

    public void runSync(Runnable runnable) {
        scheduler.runTask(plugin, runnable);
    }

    public void runSyncDelayed(Runnable runnable, long delay) {
        scheduler.runTaskLater(plugin, runnable, delay);
    }

    public void runAsyncRepeating(Runnable runnable, long delay, long period) {
        scheduler.runTaskTimerAsynchronously(plugin, runnable, delay, period);
    }

    public void startAutoSaveTask() {
        int interval = plugin.getConfigManager().getAutoSaveInterval() * 20; // Convert to ticks
        runAsyncRepeating(() -> {
            plugin.getCoinManager().saveAllData();
            plugin.getMultiplierManager().saveAllData();
            plugin.getFarmManager().saveAllData();
        }, interval, interval);
    }

    public void startAutoRegenTask() {
        int interval = plugin.getConfigManager().getAutoRegenInterval() * 20; // Convert to ticks
        runAsyncRepeating(() -> {
            // Check for crops to regenerate
        }, interval, interval);
    }

    public void startActionBarTask() {
        if (!plugin.getConfigManager().isActionBarEnabled()) return;
        
        int interval = plugin.getConfigManager().getActionBarUpdateInterval();
        runAsyncRepeating(() -> {
            plugin.getServer().getOnlinePlayers().forEach(player -> {
                long coins = plugin.getCoinManager().getCoins(player);
                long multiplier = plugin.getMultiplierManager().getMultiplier(player);
                int discount = plugin.getDiscountManager().getCurrentDiscount();
                
                String format = plugin.getConfigManager().getActionBarFormat()
                        .replace("{coins}", String.valueOf(coins))
                        .replace("{multiplier}", String.valueOf(multiplier))
                        .replace("{discount}", String.valueOf(discount));
                
                player.sendActionBar(MessageUtil.colorize(format));
            });
        }, interval, interval);
    }

    public void startBossBarTask() {
        if (!plugin.getConfigManager().isBossBarEnabled()) return;
        
        int interval = plugin.getConfigManager().getBossBarUpdateInterval();
        runAsyncRepeating(() -> {
            // BossBar implementation
        }, interval, interval);
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
