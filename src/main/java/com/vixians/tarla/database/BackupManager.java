package com.vixians.tarla.database;

import com.vixians.tarla.TarlaPlugin;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupManager {
    private TarlaPlugin plugin;
    private File backupDir;

    public BackupManager(TarlaPlugin plugin) {
        this.plugin = plugin;
        this.backupDir = new File(plugin.getDataFolder(), "backups");
        if (!backupDir.exists()) {
            backupDir.mkdirs();
        }
    }

    public void createBackup() {
        try {
            File dbFile = new File(plugin.getDataFolder(), "database.db");
            if (!dbFile.exists()) return;

            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            File backupFile = new File(backupDir, "database_" + timestamp + ".db");

            copyFile(dbFile, backupFile);
            plugin.getLogger().info("✓ Database backup created: " + backupFile.getName());
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to create backup: " + e.getMessage());
        }
    }

    private void copyFile(File source, File dest) throws IOException {
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        }
    }

    public void cleanOldBackups(int maxBackups) {
        File[] backups = backupDir.listFiles((dir, name) -> name.startsWith("database_"));
        if (backups != null && backups.length > maxBackups) {
            // Sort by date
            java.util.Arrays.sort(backups, (a, b) -> Long.compare(a.lastModified(), b.lastModified()));
            // Delete oldest
            for (int i = 0; i < backups.length - maxBackups; i++) {
                if (backups[i].delete()) {
                    plugin.getLogger().info("Deleted old backup: " + backups[i].getName());
                }
            }
        }
    }
}
