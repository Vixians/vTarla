package com.vixians.tarla.discount;

public class DiscountData {
    private int discountPercentage;
    private long setTime;
    private String setBy;

    public DiscountData(int discountPercentage, long setTime, String setBy) {
        this.discountPercentage = discountPercentage;
        this.setTime = setTime;
        this.setBy = setBy;
    }

    // Getters
    public int getDiscountPercentage() { return discountPercentage; }
    public long getSetTime() { return setTime; }
    public String getSetBy() { return setBy; }
}
