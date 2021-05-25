package com.example.myapplication.models;

import com.example.myapplication.R;

import java.io.Serializable;

public class TableItem implements Serializable {
    private String name;
    private int status;
    // empty:0
    // booked:-1
    //filled:1
    private String color;
    private int image;
    private  int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public TableItem(int id){
        this.id = id;
        this.status = 0;
        this.image = R.drawable.table_icon;
        this.color = "#4EC33A";
    }
    public TableItem(int id, int status){
        this.id = id;
        this.status = status;
        this.image = R.drawable.table_icon;
        this.color = "#4EC33A";
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