package com.vixians.tarla.market;

public class MarketItem {
    private String key;
    private String name;
    private String material;
    private long price;
    private String description;

    public MarketItem(String key, String name, String material, long price, String description) {
        this.key = key;
        this.name = name;
        this.material = material;
        this.price = price;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getMaterial() {
        return material;
    }

    public long getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
