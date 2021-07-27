package com.example.myapplication.models;

public class CountByUserItem {
    private int STT;
    private String fullname;
    private int totaldishes;

    public CountByUserItem(int STT, String fullname, int totaldishes) {
        this.STT = STT;
        this.fullname = fullname;
        this.totaldishes = totaldishes;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getTotaldishes() {
        return totaldishes;
    }

    public void setTotaldishes(int totaldishes) {
        this.totaldishes = totaldishes;
    }
}
