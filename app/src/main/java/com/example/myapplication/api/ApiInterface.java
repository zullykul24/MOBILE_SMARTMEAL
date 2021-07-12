package com.example.myapplication.api;

import com.example.myapplication.models.Account;
import com.example.myapplication.models.CreateOrder;
import com.example.myapplication.models.FoodOrderItem;
import com.example.myapplication.models.MenuFoodItem;
import com.example.myapplication.models.Table;
import com.example.myapplication.models.UploadFoodItem;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


    public interface ApiInterface {

        @GET("Account/{username}")
        Call<List<Account>> getAccount(@Path("username") String username);

        @POST("Account")
        Call<ResponseBody> createAccount(@Body Account account);

        @POST("Dish")
        Call<ResponseBody> addNewFood(@Body UploadFoodItem dishInsert);

        @GET("Dish")
        Call<List<MenuFoodItem>> getListDishes();

        @GET("Table")
        Call<List<Table>> getListTable();

        @PUT("Table/{id}")
        Call<ResponseBody> updateTableStatus(@Path("id") String id, @Body String status);

        @GET("Dish/Table/{tableId}")
        Call<List<FoodOrderItem>> getListBookedByTable(@Path("tableId") int tableId);

        @POST("OrderDetail/Table")
        Call<ResponseBody> postOrder(@Body List<FoodOrderItem> list);

        @POST("Orders")
        Call<ResponseBody> createOrder(@Body CreateOrder order);

        @GET("OrderDetail/isOrdered")
        Call<List<FoodOrderItem>> getListOrdered();

        @GET("OrderDetail/isReady")
        Call<List<FoodOrderItem>> getListReady();

        @PUT("OrderDetail/OrderDetailId/{orderDetailId}")
        Call<ResponseBody> putDishReady(@Path("orderDetailId") int orderDetailId, @Body String status);
    }

