package com.example.myapplication.models;

public class PayBillItem {
    private int STT;
    private String dishName;
    private int SL;
    private double priceEach;
    private double priceTotal;

    public PayBillItem(int STT, String dishName, int SL, double priceEach) {
        this.STT = STT;
        this.dishName = dishName;
        this.SL = SL;
        this.priceEach = priceEach;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getSL() {
        return SL;
    }

    public void setSL(int SL) {
        this.SL = SL;
    }

    public double getPriceEach() {
        return priceEach;
    }

    public void setPriceEach(double priceEach) {
        this.priceEach = priceEach;
    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(double priceTota) {
        this.priceTotal = priceTota;
    }

    public PayBillItem(String dishName, int SL, double priceEach) {
        this.dishName = dishName;
        this.SL = SL;
        this.priceEach = priceEach;
        this.priceTotal = this.SL * priceEach;
    }


}
