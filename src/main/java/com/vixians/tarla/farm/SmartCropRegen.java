package com.vixians.tarla.farm;

import com.vixians.tarla.TarlaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import java.util.HashMap;
import java.util.Map;

public class SmartCropRegen {
    private TarlaPlugin plugin;
    private Map<String, Long> regeneratingCrops = new HashMap<>();
    private boolean taskRunning = false;

    public SmartCropRegen(TarlaPlugin plugin) {
        this.plugin = plugin;
        startSmartRegenTask();
    }

    private void startSmartRegenTask() {
        int interval = plugin.getConfigManager().getAutoRegenInterval();
        
        plugin.getTaskManager().runAsyncRepeating(() -> {
            // Only regen if there are players in farm world
            String farmWorldName = plugin.getConfigManager().getFarmWorld();
            World farmWorld = Bukkit.getWorld(farmWorldName);
            
            if (farmWorld == null || farmWorld.getPlayers().isEmpty()) {
                return; // Skip if no players online
            }

            // Regenerate crops that are ready
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
            
            String farmWorldName = plugin.getConfigManager().getFarmWorld();
            World world = Bukkit.getWorld(farmWorldName);
            
            if (world == null) return;
            
            Location loc = new Location(world, x, y, z);
            Block block = loc.getBlock();
            
            if (isCrop(block.getType())) {
                block.setType(block.getType());
                plugin.getLogger().info("✓ Regenerated crop at " + location);
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Error regenerating crop: " + e.getMessage());
        }
    }

    public void recordCropBreak(String location) {
        regeneratingCrops.put(location, System.currentTimeMillis());
    }

    private boolean isCrop(Material material) {
        String name = material.name();
        return name.contains("WHEAT") || name.contains("CARROT") || 
               name.contains("POTATO") || name.contains("BEETROOT") ||
               name.contains("NETHER_WART") || name.contains("COCOA");
    }
}
