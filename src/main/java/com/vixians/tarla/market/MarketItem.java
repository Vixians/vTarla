package com.vixians.tarla.market;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

public class MarketItem {
    private String key;
    private String name;
    private String material;
    private long price;
    private int amount;
    private String command;
    private List<String> lore;

    public MarketItem(String key, String name, String material, long price, int amount, String command) {
        this.key = key;
        this.name = name;
        this.material = material;
        this.price = price;
        this.amount = amount;
        this.command = command;
        this.lore = new ArrayList<>();
    }

    public ItemStack toItemStack() {
        try {
            ItemStack stack = new ItemStack(Material.valueOf(material), amount);
            ItemMeta meta = stack.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(translateColor(name));
                List<String> coloredLore = new ArrayList<>();
                for (String line : lore) {
                    coloredLore.add(translateColor(line));
                }
                meta.setLore(coloredLore);
                stack.setItemMeta(meta);
            }
            return stack;
        } catch (Exception e) {
            return new ItemStack(Material.STONE);
        }
    }

    private String translateColor(String text) {
        return text.replace("&", "§");
    }

    // Getters
    public String getKey() { return key; }
    public String getName() { return name; }
    public String getMaterial() { return material; }
    public long getPrice() { return price; }
    public int getAmount() { return amount; }
    public String getCommand() { return command; }
    public List<String> getLore() { return lore; }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }
}
