package com.example.myapplication.models;

import java.io.Serializable;

public class HistoryItem implements Serializable {
    private int OrderId, TableId;
    private int TotalPrice;
    private String TimePayment;
    private String CashierName;

    public HistoryItem(int orderId, int tableId, int totalPrice, String timePayment, String cashierName) {
        OrderId = orderId;
        TableId = tableId;
        TotalPrice = totalPrice;
        TimePayment = timePayment;
        CashierName = cashierName;
    }

    public String getCashierName() {
        return CashierName;
    }

    public void setCashierName(String cashierName) {
        CashierName = cashierName;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        this.OrderId = orderId;
    }

    public int getTableId() {
        return TableId;
    }

    public void setTableId(int tableId) {
        this.TableId = tableId;
    }

    public int getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.TotalPrice = totalPrice;
    }

    public String getTimePayment() {
        return TimePayment;
    }

    public void setTimePayment(String timePayment) {
        this.TimePayment = timePayment;
    }
}
