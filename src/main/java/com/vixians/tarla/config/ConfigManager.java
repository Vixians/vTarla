package com.vixians.tarla.config;

import com.vixians.tarla.TarlaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {
    private TarlaPlugin plugin;
    private FileConfiguration config;
    private File configFile;

    public ConfigManager(TarlaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        setDefaults();
    }

    private void setDefaults() {
        // Farm Settings
        config.addDefault("farm.world", "world_farm");
        config.addDefault("farm.coins-per-crop", 10);
        config.addDefault("farm.auto-regen-interval", 300);

        // Message Settings
        config.addDefault("messages.prefix", "&6[vTarla] &r");
        config.addDefault("messages.harvest", "&aYou harvested {crop} and earned &6{coins} coins!");
        config.addDefault("messages.market-title", "&6Market");

        // ActionBar Settings
        config.addDefault("actionbar.enabled", true);
        config.addDefault("actionbar.format", "&6Coins: &e{coins} &6| &aMultiplier: &e{multiplier}x &6| &cDiscount: &e{discount}%");
        config.addDefault("actionbar.update-interval", 2);

        // BossBar Settings
        config.addDefault("bossbar.enabled", true);
        config.addDefault("bossbar.update-interval", 5);

        // Auto-Save Settings
        config.addDefault("auto-save.enabled", true);
        config.addDefault("auto-save.interval", 300);

        config.options().copyDefaults(true);
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
        setDefaults();
    }

    // Getters
    public String getFarmWorld() {
        return config.getString("farm.world", "world_farm");
    }

    public long getCoinsPerCrop() {
        return config.getLong("farm.coins-per-crop", 10);
    }

    public int getAutoRegenInterval() {
        return config.getInt("farm.auto-regen-interval", 300);
    }

    public String getMessagePrefix() {
        return config.getString("messages.prefix", "&6[vTarla] &r");
    }

    public String getHarvestMessage() {
        return config.getString("messages.harvest", "&aYou harvested {crop} and earned &6{coins} coins!");
    }

    public String getMarketTitle() {
        return config.getString("messages.market-title", "&6Market");
    }

    public boolean isActionBarEnabled() {
        return config.getBoolean("actionbar.enabled", true);
    }

    public String getActionBarFormat() {
        return config.getString("actionbar.format", "&6Coins: &e{coins} &6| &aMultiplier: &e{multiplier}x &6| &cDiscount: &e{discount}%");
    }

    public int getActionBarUpdateInterval() {
        return config.getInt("actionbar.update-interval", 2);
    }

    public boolean isBossBarEnabled() {
        return config.getBoolean("bossbar.enabled", true);
    }

    public int getBossBarUpdateInterval() {
        return config.getInt("bossbar.update-interval", 5);
    }
}
