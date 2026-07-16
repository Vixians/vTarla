package com.vixians.tarla.database;

import org.bukkit.plugin.java.JavaPlugin;
import java.sql.*;
import java.io.File;

public class DatabaseManager {
    private JavaPlugin plugin;
    private Connection connection;
    private String dbPath;

    public DatabaseManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.dbPath = plugin.getDataFolder() + "/database.db";
        initializeDatabase();
    }

    private void initializeDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            connection.setAutoCommit(false);
            createTables();
            plugin.getLogger().info("§aDatabase initialized successfully!");
        } catch (Exception e) {
            plugin.getLogger().severe("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createTables() {
        try {
            Statement stmt = connection.createStatement();

            // Coins table
            stmt.execute("CREATE TABLE IF NOT EXISTS player_coins (" +
                    "uuid TEXT PRIMARY KEY," +
                    "coins LONG DEFAULT 0," +
                    "last_updated LONG DEFAULT " + System.currentTimeMillis() + ")" );

            // Multipliers table
            stmt.execute("CREATE TABLE IF NOT EXISTS player_multipliers (" +
                    "uuid TEXT PRIMARY KEY," +
                    "multiplier_tier TEXT DEFAULT 'default'," +
                    "multiplier_value REAL DEFAULT 1.0," +
                    "last_updated LONG DEFAULT " + System.currentTimeMillis() + ")");

            // Farm data table
            stmt.execute("CREATE TABLE IF NOT EXISTS farm_breaks (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "uuid TEXT," +
                    "location TEXT," +
                    "crop_type TEXT," +
                    "break_time LONG," +
                    "coins_earned LONG)");

            // Discount history table
            stmt.execute("CREATE TABLE IF NOT EXISTS discount_history (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "discount_percentage INTEGER," +
                    "set_time LONG," +
                    "set_by TEXT)");

            // Market purchases table
            stmt.execute("CREATE TABLE IF NOT EXISTS market_purchases (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "uuid TEXT," +
                    "item_name TEXT," +
                    "price LONG," +
                    "purchase_time LONG)");

            connection.commit();
            plugin.getLogger().info("§aAll database tables created successfully!");
        } catch (SQLException e) {
            plugin.getLogger().severe("Error creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void executeUpdate(String sql, Object... params) {
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            plugin.getLogger().severe("SQL Update Error: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public ResultSet executeQuery(String sql, Object... params) {
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeQuery();
        } catch (SQLException e) {
            plugin.getLogger().severe("SQL Query Error: " + e.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                plugin.getLogger().info("§aDatabase connection closed!");
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Error closing database: " + e.getMessage());
        }
    }
}
