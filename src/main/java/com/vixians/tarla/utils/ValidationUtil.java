package com.vixians.tarla.utils;

public class ValidationUtil {
    public static boolean isValidPlayer(String playerName) {
        return playerName != null && !playerName.isEmpty() && playerName.length() <= 16;
    }

    public static boolean isValidCoinAmount(long amount) {
        return amount > 0 && amount < Long.MAX_VALUE;
    }

    public static boolean isValidDiscount(int discount) {
        return discount >= 0 && discount <= 100;
    }

    public static boolean isValidPrice(long price) {
        return price >= 0 && price < Long.MAX_VALUE;
    }

    public static boolean isValidMultiplier(double multiplier) {
        return multiplier > 0 && multiplier <= 100;
    }
}
