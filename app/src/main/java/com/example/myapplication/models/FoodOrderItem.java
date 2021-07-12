package com.example.myapplication.models;

import java.io.Serializable;

public class FoodOrderItem extends MenuFoodItem implements Serializable {

    private int quantityOrder;
    private int accountId;
    private int tableId;
    private int isBooked = 0;
    private int OrderDetailId;


    public FoodOrderItem(int dishId, String dish_name, int price, int dishType_id, String image, int quantityOrder, int isBooked) {
        super(dishId, dish_name, price, dishType_id, image);
        this.quantityOrder = quantityOrder;
        this.isBooked = isBooked;
    }
    public FoodOrderItem(int tableId, int dishId, int accountId, int quantityOrder){
        super(dishId, "",0,0,"");
        this.quantityOrder = quantityOrder;
        this.accountId = accountId;
        this.tableId = tableId;
        this.quantityOrder = quantityOrder;
    }
    public FoodOrderItem(int dishId, int tableId, String dishName, int quantityOrder, String image, int dishTypeId, int OrderDetailId){
        super(dishId, dishName,0, dishTypeId, image);
        this.tableId = tableId;
        this.quantityOrder = quantityOrder;
        this.OrderDetailId = OrderDetailId;
    }

    public int getOrderDetailId() {
        return OrderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        OrderDetailId = orderDetailId;
    }

    public int getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(int isBooked) {
        this.isBooked = isBooked;
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