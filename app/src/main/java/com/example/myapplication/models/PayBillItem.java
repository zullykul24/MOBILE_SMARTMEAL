package com.example.myapplication.models;

public class PayBillItem {
    private int STT;
    private String dishName;
    private int quantityOrder;
    private int price;
    private int priceTotal;

    public PayBillItem(int STT, String dishName, int quantityOrder, int price) {
        this.STT = STT;
        this.dishName = dishName;
        this.quantityOrder = quantityOrder;
        this.price = price;
        this.priceTotal = this.quantityOrder * price;
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

    public int getQuantityOrder() {
        return quantityOrder;
    }

    public void setQuantityOrder(int quantityOrder) {
        this.quantityOrder = quantityOrder;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPriceTotal(int priceTotal) {
        this.priceTotal = priceTotal;
    }

    public int getPriceTotal() {
        return priceTotal;
    }




}
