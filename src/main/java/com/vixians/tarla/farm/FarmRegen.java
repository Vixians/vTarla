package com.vixians.tarla.farm;

import com.vixians.tarla.TarlaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

public class FarmRegen {
    private TarlaPlugin plugin;
    private Map<String, Long> regeneratingCrops = new HashMap<>();

    public FarmRegen(TarlaPlugin plugin) {
        this.plugin = plugin;
        startRegenTask();
    }

    private void startRegenTask() {
        int interval = plugin.getConfigManager().getAutoRegenInterval();
        plugin.getTaskManager().runAsyncRepeating(() -> {
            regeneratingCrops.forEach((location, breakTime) -> {
                if (System.currentTimeMillis() - breakTime >= (interval * 1000L)) {
                    regenerateCropAtLocation(location);
                    regeneratingCrops.remove(location);
                }
            });
        }, interval * 20L, interval * 20L);
    }

    private void regenerateCropAtLocation(String location) {
        try {
            String[] parts = location.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            int z = Integer.parseInt(parts[2]);
            
            Location loc = new Location(Bukkit.getWorld(plugin.getConfigManager().getFarmWorld()), x, y, z);
            Block block = loc.getBlock();
            
            // Restore crop to full growth
            if (isCrop(block.getType())) {
                block.setType(block.getType());
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Error regenerating crop: " + e.getMessage());
        }
    }

    public void addRegeneratingCrop(String location) {
        regeneratingCrops.put(location, System.currentTimeMillis());
    }

    private boolean isCrop(Material material) {
        String name = material.name();
        return name.contains("WHEAT") || name.contains("CARROT") || 
               name.contains("POTATO") || name.contains("BEETROOT") ||
               name.contains("NETHER_WART") || name.contains("COCOA");
    }
}
