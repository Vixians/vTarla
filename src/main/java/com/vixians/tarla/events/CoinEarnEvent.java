package com.vixians.tarla.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a player earns Tarla coins
 */
public class CoinEarnEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private Player player;
    private long amount;
    private long multipliedAmount;

    public CoinEarnEvent(Player player, long amount, long multipliedAmount) {
        this.player = player;
        this.amount = amount;
        this.multipliedAmount = multipliedAmount;
    }

    public Player getPlayer() {
        return player;
    }

    public long getAmount() {
        return amount;
    }

    public long getMultipliedAmount() {
        return multipliedAmount;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
