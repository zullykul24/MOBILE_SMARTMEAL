package com.example.myapplication.models;

public class CreateOrder {
    private int orderId;
    private int tableId;
    private int status;

    public CreateOrder(int orderId, int tableId, int status) {
        this.orderId = orderId;
        this.tableId = tableId;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
