package com.example.myapplication.data_local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.Account;

public class MySharedPreferences {
    private static String TAG = MySharedPreferences.class.getName();
    Context context;

    private static final String NAME = "android_demo";
    private static final String KEY_LOGIN = "isLogin";


    public MySharedPreferences(Context context) {
        this.context = context;
    }
    public void putIsLogin(String key, boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getIsLogin(String key){
       SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
       return sharedPreferences.getBoolean(key, false);
    }
    public void putAccount(String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String getAccount(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

}
