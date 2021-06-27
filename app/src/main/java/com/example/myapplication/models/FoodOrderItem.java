package com.example.myapplication.models;

import java.io.Serializable;

public class FoodOrderItem extends MenuFoodItem implements Serializable {

    private int number;


    public FoodOrderItem(int dishID, String dish_name, int price, int dishType_id, String imageUrl) {
        super(dishID, dish_name, price, dishType_id, imageUrl);
        this.number = 1;
    }




    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }





}