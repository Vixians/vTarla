package com.vixians.tarla.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a player purchases a multiplier
 */
public class MultiplierPurchaseEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private Player player;
    private String tier;
    private double multiplier;
    private long price;

    public MultiplierPurchaseEvent(Player player, String tier, double multiplier, long price) {
        this.player = player;
        this.tier = tier;
        this.multiplier = multiplier;
        this.price = price;
    }

    public Player getPlayer() {
        return player;
    }

    public String getTier() {
        return tier;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public long getPrice() {
        return price;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
