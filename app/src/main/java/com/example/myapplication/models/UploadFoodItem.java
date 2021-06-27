package com.example.myapplication.models;

public class UploadFoodItem {
    private String dishName;
    private int price;
    private int dishTypeId;
    private String base64ImageString;
    public UploadFoodItem(String dishName, int price, int dishTypeId, String base64ImageString){
        this.dishName = dishName;
        this.price = price;
        this.dishTypeId = dishTypeId;
        this.base64ImageString = base64ImageString;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDishTypeId() {
        return dishTypeId;
    }

    public void setDishTypeId(int dishTypeId) {
        this.dishTypeId = dishTypeId;
    }

    public String getBase64ImageString() {
        return base64ImageString;
    }

    public void setBase64ImageString(String base64ImageString) {
        this.base64ImageString = base64ImageString;
    }
}
