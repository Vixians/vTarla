package com.vixians.tarla.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        loadConfig();
    }

    public void loadConfig() {
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void reloadConfig() {
        loadConfig();
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save config: " + e.getMessage());
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    // Farm settings
    public String getFarmWorld() {
        return config.getString("farm.world", "farm_world");
    }

    public void setFarmWorld(String world) {
        config.set("farm.world", world);
        saveConfig();
    }

    public int getAutoSaveInterval() {
        return config.getInt("farm.auto-save-interval", 300);
    }

    public int getAutoRegenInterval() {
        return config.getInt("farm.auto-regen-interval", 600);
    }

    // Coin settings
    public int getCoinsPerCrop() {
        return config.getInt("coin.coins-per-crop", 10);
    }

    public String getCoinDisplayName() {
        return config.getString("coin.display-name", "&6Tarla Coin&r");
    }

    public String getCoinEarnMessage() {
        return config.getString("coin.earn-message", "&a+{coins} {coin_name}");
    }

    public int getDatabaseSaveInterval() {
        return config.getInt("coin.database-save-interval", 300);
    }

    // Multiplier settings
    public boolean isMultiplierEnabled() {
        return config.getBoolean("multiplier.enabled", true);
    }

    public double getDefaultMultiplier() {
        return config.getDouble("multiplier.default-multiplier", 1.0);
    }

    // Discount settings
    public boolean isDiscountEnabled() {
        return config.getBoolean("discount.enabled", true);
    }

    public int getCurrentDiscount() {
        return config.getInt("discount.current-discount", 0);
    }

    public void setCurrentDiscount(int discount) {
        config.set("discount.current-discount", Math.min(discount, getMaxDiscount()));
        saveConfig();
    }

    public int getMaxDiscount() {
        return config.getInt("discount.max-discount", 50);
    }

    // Market settings
    public String getMarketTitle() {
        return config.getString("market.title", "&6Tarla Market");
    }

    // Message settings
    public String getMessagePrefix() {
        return config.getString("messages.prefix", "&6[vTarla] &r");
    }

    public String getMessage(String key) {
        String message = config.getString("messages." + key);
        return message != null ? message : "Missing message: " + key;
    }

    // Display settings
    public boolean isActionBarEnabled() {
        return config.getBoolean("display.actionbar.enabled", true);
    }

    public String getActionBarFormat() {
        return config.getString("display.actionbar.format", "&6Coins: {coins}");
    }

    public int getActionBarUpdateInterval() {
        return config.getInt("display.actionbar.update-interval", 60);
    }

    public boolean isBossBarEnabled() {
        return config.getBoolean("display.bossbar.enabled", true);
    }

    public String getBossBarTitle() {
        return config.getString("display.bossbar.title", "&6Tarla Coins: {coins}");
    }

    public String getBossBarColor() {
        return config.getString("display.bossbar.color", "GOLD");
    }

    public String getBossBarStyle() {
        return config.getString("display.bossbar.style", "SEGMENTED_10");
    }

    public int getBossBarUpdateInterval() {
        return config.getInt("display.bossbar.update-interval", 60);
    }
}
