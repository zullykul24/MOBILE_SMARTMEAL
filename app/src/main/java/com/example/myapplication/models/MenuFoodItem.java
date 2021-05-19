package com.example.myapplication.models;

import java.io.Serializable;

public class MenuFoodItem implements Serializable {
    private int dishId;
    private String dishName;
    private int dishTypeId;
    private int price;
    private String image;
    private String base64ImageString;

    @Override
    public String toString() {
        return "MenuFoodItem{" +
                "dishName='" + dishName + '\'' +
                ", dishTypeId=" + dishTypeId +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }

    public MenuFoodItem(String dishName, int price, int dishTypeId, String image){

        this.dishName = dishName;
        this.price = price;
        this.image = image;
        this.dishTypeId = dishTypeId;
    }
    public MenuFoodItem(int dishId, String dishName, int price, int dishTypeId, String base64ImageString){
        this.dishId = dishId;
        this.dishName = dishName;
        this.price = price;
        this.dishTypeId = dishTypeId;
        this.base64ImageString = base64ImageString;
    }

    public String getBase64ImageString() {
        return base64ImageString;
    }

    public void setBase64ImageString(String base64ImageString) {
        this.base64ImageString = base64ImageString;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getDishTypeId() {
        return dishTypeId;
    }

    public void setDishTypeId(int group_id) {
        this.dishTypeId = group_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
