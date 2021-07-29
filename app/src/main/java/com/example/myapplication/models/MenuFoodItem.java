package com.example.myapplication.models;

import com.example.myapplication.VNCharacterUtils;

import java.io.Serializable;

public class MenuFoodItem implements Serializable, Comparable<MenuFoodItem> {
    private int dishId;
    private String dishName;
    private int dishTypeId;
    private int price;
    private String image;


    @Override
    public String toString() {
        return "MenuFoodItem{" +
                "dishId=" + dishId +
                ", dishName='" + dishName + '\'' +
                ", dishTypeId=" + dishTypeId +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }




    public MenuFoodItem(int dishId,String dishName, int price, int dishTypeId, String image){
        this.dishId = dishId;
        this.dishName = dishName;
        this.price = price;
        this.image = image;
        this.dishTypeId = dishTypeId;
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


    @Override
    public int compareTo(MenuFoodItem o) {
        return VNCharacterUtils.removeAccent(this.dishName.toLowerCase())
                .compareTo(VNCharacterUtils.removeAccent(o.getDishName().toLowerCase()));
    }
}
