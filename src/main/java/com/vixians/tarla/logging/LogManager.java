package com.vixians.tarla.logging;

import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogManager {
    private JavaPlugin plugin;
    private File logFile;
    private FileWriter fileWriter;
    private boolean enabled;

    public LogManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.enabled = plugin.getConfig().getBoolean("logging.console", true);
        
        File logsDir = new File(plugin.getDataFolder(), "logs");
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }
        
        this.logFile = new File(logsDir, "vTarla.log");
        try {
            this.fileWriter = new FileWriter(logFile, true);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to initialize logger: " + e.getMessage());
        }
    }

    public void info(String message) {
        log("INFO", message);
        if (enabled) plugin.getLogger().info(message);
    }

    public void warning(String message) {
        log("WARNING", message);
        if (enabled) plugin.getLogger().warning(message);
    }

    public void severe(String message) {
        log("SEVERE", message);
        if (enabled) plugin.getLogger().severe(message);
    }

    public void debug(String message) {
        log("DEBUG", message);
    }

    private void log(String level, String message) {
        try {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String logMessage = "[" + timestamp + "] [" + level + "] " + message + "\n";
            fileWriter.write(logMessage);
            fileWriter.flush();
        } catch (IOException e) {
            if (enabled) plugin.getLogger().severe("Failed to write to log file: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to close log file: " + e.getMessage());
        }
    }
}
