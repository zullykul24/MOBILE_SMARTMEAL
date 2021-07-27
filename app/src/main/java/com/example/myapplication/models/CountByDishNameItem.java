package com.example.myapplication.models;

public class CountByDishNameItem {
    private int STT;
    private String dishName;
    private int sumQuantity;

    public CountByDishNameItem(int STT, String dishName, int sumQuantity) {
        this.dishName = dishName;
        this.sumQuantity = sumQuantity;
        this.STT = STT;
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

    public int getSumQuantity() {
        return sumQuantity;
    }

    public void setSumQuantity(int sumQuantity) {
        this.sumQuantity = sumQuantity;
    }
}
