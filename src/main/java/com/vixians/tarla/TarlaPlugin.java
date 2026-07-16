package com.vixians.tarla;

import com.vixians.tarla.config.ConfigManager;
import com.vixians.tarla.database.DatabaseManager;
import com.vixians.tarla.coin.CoinManager;
import com.vixians.tarla.multiplier.MultiplierManager;
import com.vixians.tarla.market.MarketManager;
import com.vixians.tarla.farm.FarmManager;
import com.vixians.tarla.discount.DiscountManager;
import com.vixians.tarla.commands.*;
import com.vixians.tarla.listeners.*;
import com.vixians.tarla.utils.TaskManager;
import com.vixians.tarla.utils.GUIUtil;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public class TarlaPlugin extends JavaPlugin {
    private static TarlaPlugin instance;
    private ConfigManager configManager;
    private DatabaseManager databaseManager;
    private CoinManager coinManager;
    private MultiplierManager multiplierManager;
    private MarketManager marketManager;
    private FarmManager farmManager;
    private DiscountManager discountManager;
    private TaskManager taskManager;

    @Override
    public void onEnable() {
        instance = this;
        long startTime = System.currentTimeMillis();

        getLogger().info("&6vTarla Plugin starting...");

        // Initialize managers in correct order
        this.configManager = new ConfigManager(this);
        this.databaseManager = new DatabaseManager(this);
        this.coinManager = new CoinManager(this);
        this.multiplierManager = new MultiplierManager(this);
        this.marketManager = new MarketManager(this);
        this.farmManager = new FarmManager(this);
        this.discountManager = new DiscountManager(this);
        this.taskManager = new TaskManager(this);
        
        // Initialize GUIUtil with plugin reference
        new GUIUtil(this);

        // Register commands
        registerCommands();

        // Register listeners
        registerListeners();

        // Start async tasks
        startAsyncTasks();

        long loadTime = System.currentTimeMillis() - startTime;
        getLogger().info("&avTarla Plugin enabled! (" + loadTime + "ms)");
    }

    @Override
    public void onDisable() {
        getLogger().info("&cvTarla Plugin disabling...");

        // Save all data
        if (coinManager != null) {
            coinManager.saveAllData();
        }
        if (multiplierManager != null) {
            multiplierManager.saveAllData();
        }
        if (farmManager != null) {
            farmManager.saveAllData();
        }
        if (databaseManager != null) {
            databaseManager.close();
        }
        if (taskManager != null) {
            taskManager.shutdown();
        }

        getLogger().info("&avTarla Plugin disabled!");
    }

    private void registerCommands() {
        getCommand("tarla").setExecutor(new TarlaCommand(this));
        getCommand("tarlamarket").setExecutor(new TarlaMarketCommand(this));
        getCommand("tarlamultiplier").setExecutor(new TarlaMultiplierCommand(this));
        getCommand("taladiscount").setExecutor(new TarlaDiscountCommand(this));
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new CropBreakListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(this), this);
    }

    private void startAsyncTasks() {
        taskManager.startAutoSaveTask();
        taskManager.startAutoRegenTask();
        taskManager.startActionBarTask();
        taskManager.startBossBarTask();
    }

    public static TarlaPlugin getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public CoinManager getCoinManager() {
        return coinManager;
    }

    public MultiplierManager getMultiplierManager() {
        return multiplierManager;
    }

    public MarketManager getMarketManager() {
        return marketManager;
    }

    public FarmManager getFarmManager() {
        return farmManager;
    }

    public DiscountManager getDiscountManager() {
        return discountManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }
}
