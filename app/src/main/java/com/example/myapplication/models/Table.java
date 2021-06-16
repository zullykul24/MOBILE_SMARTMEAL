package com.example.myapplication.models;

import com.example.myapplication.R;

import java.io.Serializable;

public class Table implements Serializable {
    private String name;
    private int status;
    // empty:0
    // booked:-1
    //filled:1
    private String color;
    private int image;
    private  int tableId;
    private  int seatAmount;
    public int getTableId() {
        return tableId;
    }
    public void setTableId(int tableId) {
        this.tableId = tableId;
    }


    public int getSeatAmount() {
        return seatAmount;
    }

    public void setSeatAmount(int seatAmount) {
        this.seatAmount = seatAmount;
    }

    public Table(int tableId, int seatAmount, int Status) {
        this.status = Status;
        this.tableId = tableId;
        this.seatAmount = seatAmount;
        this.color = "#4EC33A";
        this.image = R.drawable.table_icon;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}