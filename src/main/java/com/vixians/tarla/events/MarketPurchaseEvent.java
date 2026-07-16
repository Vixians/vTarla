package com.vixians.tarla.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a player makes a market purchase
 */
public class MarketPurchaseEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private Player player;
    private String itemKey;
    private long price;

    public MarketPurchaseEvent(Player player, String itemKey, long price) {
        this.player = player;
        this.itemKey = itemKey;
        this.price = price;
    }

    public Player getPlayer() {
        return player;
    }

    public String getItemKey() {
        return itemKey;
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
