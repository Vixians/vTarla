package com.vixians.tarla.discount;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.database.DatabaseManager;

import java.sql.ResultSet;

public class DiscountManager {
    private TarlaPlugin plugin;
    private DatabaseManager db;
    private int currentDiscount = 0;

    public DiscountManager(TarlaPlugin plugin) {
        this.plugin = plugin;
        this.db = plugin.getDatabaseManager();
        loadCurrentDiscount();
    }

    public int getCurrentDiscount() {
        return currentDiscount;
    }

    public void setDiscount(int percentage) {
        if (percentage < 0 || percentage > 100) {
            plugin.getLogger().warning("Invalid discount percentage: " + percentage);
            return;
        }
        
        this.currentDiscount = percentage;
        updateDatabase(percentage);
        plugin.getLogger().info("✓ Discount set to " + percentage + "%");
    }

    private void updateDatabase(int percentage) {
        db.executeUpdate(
                "INSERT INTO discount_history (discount_percentage, set_time, set_by) VALUES (?, ?, ?)",
                percentage, System.currentTimeMillis(), "system"
        );
    }

    private void loadCurrentDiscount() {
        try {
            ResultSet rs = db.executeQuery("SELECT discount_percentage FROM discount_history ORDER BY set_time DESC LIMIT 1");
            if (rs != null && rs.next()) {
                currentDiscount = rs.getInt("discount_percentage");
                rs.close();
            }
            plugin.getLogger().info("✓ Current discount: " + currentDiscount + "%");
        } catch (Exception e) {
            plugin.getLogger().severe("Error loading discount: " + e.getMessage());
        }
    }
}
