package com.example.myapplication.data_local;

import android.content.Context;

import com.example.myapplication.Account;
import com.google.gson.Gson;


public class DataLocalManager {
    private static final String KEY_LOGIN = "KEY_LOGIN";
    private static final String KEY_ACCOUNT = "ACCOUNT";
    private static DataLocalManager instance;
    private MySharedPreferences mySharedPreferences;
    public static void init(Context context){
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }
    public static DataLocalManager getInstance(){
        if(instance == null){
            instance = new DataLocalManager();
        }
        return instance;
    }
    public static void setIsLogin(boolean isLogin){
        DataLocalManager.getInstance().mySharedPreferences.putIsLogin(KEY_LOGIN, isLogin);
    }
    public static boolean getIsLogin(){
        return DataLocalManager.getInstance().mySharedPreferences.getIsLogin(KEY_LOGIN);
    }
    public static void setLoggedinAccount(Account account){
        Gson gson = new Gson();
        String jsonAccount = gson.toJson(account);
        DataLocalManager.getInstance().mySharedPreferences.putAccount(KEY_ACCOUNT, jsonAccount);
    }
    public static Account getLoggedinAccount(){
        String jsonAccount = DataLocalManager.getInstance().mySharedPreferences.getAccount(KEY_ACCOUNT);
        Gson gson = new Gson();
        Account account = gson.fromJson(jsonAccount, Account.class);
        return account;
    }
}
