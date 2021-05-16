package com.example.myapplication.models;

import com.example.myapplication.R;

import java.io.Serializable;

public class TableItem implements Serializable {
    private String name;
    private String status;
    private String color;
    private int image;
    private  int chair_number;
    private  int id;

    public TableItem(int id, String name,int chair_number, String status) {
        this.id= id;
        this.name = name;
        this.status = status;
        this.image = R.drawable.table_icon;
        this.color = "#4EC33A";
        this.chair_number= chair_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TableItem(String name, String status){
        this.name = name;
        this.status = status;
        this.image = R.drawable.table_icon;
        this.color = "#4EC33A";
    }
    public TableItem(String name){
        this.name = name;
        this.status = "Empty";
        this.image = R.drawable.table_icon;
        this.color = "#4EC33A";
    }

    public int getChair_number() {
        return chair_number;
    }

    public void setChair_number(int chair_number) {
        this.chair_number = chair_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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