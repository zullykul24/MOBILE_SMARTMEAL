package com.example.myapplication.models;

import java.io.Serializable;

public class FoodOrderItem implements Serializable {
    private MenuFoodItem item;
    private int number;
    private String note;

    public FoodOrderItem(int dish_id, String dish_name, int price, int dishType_id, String imageUrl) {
        item = new MenuFoodItem(dish_id, dish_name, price, dishType_id, imageUrl);
        this.number = 1;
    }


    public int getDish_id() { return item.getDishId();}

    public void setDish_id(int dish_id) {
        item.setDishId(dish_id);
    }

    public String getDish_name() {
        return item.getDishName();
    }

    public void setDish_name(String dish_name) {
        item.setDishName(dish_name);
    }

    public int getDishtypeId() {
        return item.getDishTypeId();
    }

    public void setGroup_id(int dishTypeId) {
        item.setDishTypeId(dishTypeId);
    }

    public int getPrice() {
        return item.getPrice();
    }

    public void setPrice(int price) {
        item.setPrice(price);
    }

    public String getImageUrl() {
        return item.getImage();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setImage(String imageUrl) {
        item.setImage(imageUrl);
    }


    public String getNote() {return this.note;}
    public void setNote(String note) {this.note = note;}
}