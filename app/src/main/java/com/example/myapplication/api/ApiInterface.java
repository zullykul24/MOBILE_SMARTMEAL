package com.example.myapplication.api;

import com.example.myapplication.activities.Order;
import com.example.myapplication.models.Account;
import com.example.myapplication.models.CountByDishNameItem;
import com.example.myapplication.models.CountByUserItem;
import com.example.myapplication.models.CreateOrder;
import com.example.myapplication.models.FoodOrderItem;
import com.example.myapplication.models.HistoryItem;
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

        @POST("OrderDetail/TableFirstTime/{tableId}")
        Call<ResponseBody> postOrderTableFirstTime(@Body List<FoodOrderItem> list, @Path("tableId") int tableId);

        @POST("Orders/firstTime/{tableId}")
        Call<ResponseBody> createOrder(@Path("tableId") int tableId);



        @GET("OrderDetail/isOrdered")
        Call<List<FoodOrderItem>> getListOrdered();

        @GET("OrderDetail/isReady")
        Call<List<FoodOrderItem>> getListReady();

        @PUT("OrderDetail/OrderDetailId/{orderDetailId}")
        Call<ResponseBody> putDishReady(@Path("orderDetailId") int orderDetailId, @Body String status);

        @GET("Orders/isReady")
        Call<List<CreateOrder>> getPaymentTables();

        @GET("OrderDetail/GetDishesDone/{orderId}")
        Call<List<FoodOrderItem>> getListDone(@Path("orderId") int orderId);



        @POST("Orders/isPaid/{orderId}/{tableId}/{cashierId}")
        Call<ResponseBody> confirmPayment(@Path("orderId") int orderId,
                                                     @Path("tableId") int tableId,
                                                     @Path("cashierId") int cashierId);

        @GET("Payment")
        Call<List<HistoryItem>> getHistory();

        @GET("Payment/getByDate/{fromDate}/{toDate}")
        Call<List<HistoryItem>> getHistoryByDate(@Path("fromDate") String fromDate, @Path("toDate") String toDate);

        @GET("Dish/AllDishesPaid")
        Call<List<CountByDishNameItem>> getCountByDishName();

        @GET("Dish/AllDishesCountByAccount")
        Call<List<CountByUserItem>> getCountByUser();
    }

