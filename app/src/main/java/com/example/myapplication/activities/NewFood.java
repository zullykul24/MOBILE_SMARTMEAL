package com.example.myapplication.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapters.MenuFoodItemAdapter;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;
import com.example.myapplication.models.MenuFoodItem;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewFood extends AppCompatActivity {
    ListView listview ;
    ImageButton backToMain;
    ArrayList<MenuFoodItem> arrayNewFood;
    private List<MenuFoodItem> responseList;
    MenuFoodItemAdapter menuFoodItemAdapter;
    HubConnection hubConnection;
    private MenuFoodItem receivedItem;

    long millis = System.currentTimeMillis();
    java.sql.Date date = new java.sql.Date(millis);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_food2);
        listview = (ListView) findViewById(R.id.new_food);
        backToMain = (ImageButton) findViewById(R.id.back_to_main_from_newest_foods);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        arrayNewFood = new ArrayList<>();
        ////
        ///
        hubConnection = HubConnectionBuilder.create(ApiClient.BASE_URL +"addFoodHub").build();

            hubConnection.start();



        /// add món vào list này nè
        ///
        //////chỗ này lấy ảnh test thôi

        ApiClient.getApiClient().create(ApiInterface.class).getListDishes().enqueue(new Callback<List<MenuFoodItem>>() {
            @Override
            public void onResponse(Call<List<MenuFoodItem>> call, Response<List<MenuFoodItem>> response) {
                responseList = (ArrayList<MenuFoodItem>) response.body();
                for (MenuFoodItem i:responseList){
                    /// thêm tất cả các món từ response vào menuItemArrayList
                    arrayNewFood.add(0,new MenuFoodItem( i.getDishName(), i.getPrice(),i.getDishTypeId(), ApiClient.BASE_URL +"Image/" + i.getImage()));
                }
                Log.e("Get list dishes status:", "Success");
                menuFoodItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MenuFoodItem>> call, Throwable t) {
                Log.e("Get list dishes status:", "Failed"+ t);
            }
        });


        //


       ///
        //


        menuFoodItemAdapter = new MenuFoodItemAdapter(NewFood.this, R.layout.item_menu_food, arrayNewFood);
        listview.setAdapter(menuFoodItemAdapter);
        hubConnection.on("ReceiveNewFood", (foodName, foodPrice, dishType) ->{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    receivedItem = new MenuFoodItem(foodName,foodPrice, dishType, ApiClient.BASE_URL);
                    arrayNewFood.add(0, receivedItem);
                    menuFoodItemAdapter.notifyDataSetChanged();
                }
            });

            Log.e("ReceiveNewFoodmsg",foodName + foodPrice+""+ dishType+"");
        }, String.class, Integer.class, Integer.class);
    }
}