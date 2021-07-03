package com.example.myapplication.models;

import java.io.Serializable;

public class FoodOrderItem extends MenuFoodItem implements Serializable {

    private int quantityOrder;
    private int accountId;
    private int tableId;


    public FoodOrderItem(int dishId, String dish_name, int price, int dishType_id, String image, int quantityOrder) {
        super(dishId, dish_name, price, dishType_id, image);
        this.quantityOrder = quantityOrder;
    }
    public FoodOrderItem(int tableId, int dishId, int accountId, int quantityOrder){
        super(dishId, "",0,0,"");
        this.quantityOrder = quantityOrder;
        this.accountId = accountId;
        this.tableId = tableId;
        this.quantityOrder = quantityOrder;
    }


    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getQuantityOrder() {
        return quantityOrder;
    }
    public void setQuantityOrder(int quantityOrder) {
        this.quantityOrder = quantityOrder;
    }

}