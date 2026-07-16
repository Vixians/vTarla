package com.vixians.tarla.discount;

import com.vixians.tarla.TarlaPlugin;
import com.vixians.tarla.database.DatabaseManager;

public class DiscountManager {
    private TarlaPlugin plugin;
    private DatabaseManager db;
    private int currentDiscount;

    public DiscountManager(TarlaPlugin plugin) {
        this.plugin = plugin;
        this.db = plugin.getDatabaseManager();
        this.currentDiscount = plugin.getConfigManager().getCurrentDiscount();
    }

    public int getCurrentDiscount() {
        return currentDiscount;
    }

    public void setDiscount(int discount, String setBy) {
        int maxDiscount = plugin.getConfigManager().getMaxDiscount();
        if (discount > maxDiscount) {
            discount = maxDiscount;
        }
        
        this.currentDiscount = discount;
        plugin.getConfigManager().setCurrentDiscount(discount);
        
        // Save to database
        db.executeUpdate(
                "INSERT INTO discount_history (discount_percentage, set_time, set_by) VALUES (?, ?, ?)",
                discount, System.currentTimeMillis(), setBy
        );
    }

    public void removeDiscount(String removedBy) {
        setDiscount(0, removedBy);
    }
}
