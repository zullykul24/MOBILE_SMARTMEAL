package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.data_local.DataLocalManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
