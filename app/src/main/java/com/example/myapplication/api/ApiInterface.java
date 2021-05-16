package com.example.myapplication.api;

import com.example.myapplication.models.Account;
import com.example.myapplication.models.MenuFoodItem;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


    public interface ApiInterface {

        @GET("Account/{username}")
        Call<List<Account>> getAccount(@Path("username") String username);

        @POST("Account")
        Call<ResponseBody> createAccount(@Body Account account);

        @POST("Dish")
        Call<ResponseBody> addNewFood(@Body MenuFoodItem dishInsert);

        @GET("Dish")
        Call<List<MenuFoodItem>> getListDishes();
    }

