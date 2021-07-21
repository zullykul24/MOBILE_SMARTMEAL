package com.example.myapplication.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapters.KitchenFoodItemAdapter;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;
import com.example.myapplication.models.FoodOrderItem;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KitchenDrink extends AppCompatActivity {
    ListView kitchenOrderedDrinkListView;
    ImageButton backToMain;
    ArrayList<FoodOrderItem> foodOrderItemArrayList, responseList;
    KitchenFoodItemAdapter adapter;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    HubConnection hubConnection;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_drink);
        kitchenOrderedDrinkListView = (ListView)findViewById(R.id.ordered_drinklistview);
        backToMain = (ImageButton)findViewById(R.id.img_back_btn_from_kitchen_ordered_drink);
        hubConnection = HubConnectionBuilder.create(ApiClient.BASE_URL +"orderFoodHub").build();
        hubConnection.start();
        foodOrderItemArrayList = new ArrayList<>();


        adapter = new KitchenFoodItemAdapter(KitchenDrink.this,  R.layout.item_kitchen_ordered_food, foodOrderItemArrayList);


        // call api to get list orderdetails drink ordered
        API_GetListDrinkOrdered();

        kitchenOrderedDrinkListView.setAdapter(adapter);

        // nhận signalr confirm ordered food
        hubConnection.on("OrderedFoodReceived", (dishType) ->{
            if(dishType == 0){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        API_GetListDrinkOrdered();
                    }
                });
            }
            Log.e("OrderedFoodReceivedmsg",dishType+"");
        }, Integer.class);


        // click to back to main activity
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ///click when kitchen staff finish the dishes and the waiter 
        kitchenOrderedDrinkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowDialog(position);

            }
        });



    }
    private void ShowDialog(final int position){
        builder = new AlertDialog.Builder(KitchenDrink.this);
        builder.setMessage("Món đã sẵn sàng?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(KitchenDrink.this, foodOrderItemArrayList.get(position).getDishName(), Toast.LENGTH_LONG).show();
                API_PutDishReady(foodOrderItemArrayList.get(position).getOrderDetailId());
            }
        });
        builder.setNegativeButton("HUỶ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    private void API_PutDishReady(int orderDetailId) {
        ApiClient.getApiClient().create(ApiInterface.class).putDishReady(orderDetailId, "1").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    /////
                } else {
                    Toast.makeText(KitchenDrink.this, "Có lỗi xảy ra.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(KitchenDrink.this, "Có lỗi xảy ra. Vui lòng thử lại.", Toast.LENGTH_LONG).show();
                Log.e("PutDishReady failed: ", t.toString());
            }
        });
    }

    private void API_GetListDrinkOrdered() {
        ApiClient.getApiClient().create(ApiInterface.class).getListOrdered().enqueue(new Callback<List<FoodOrderItem>>() {
            @Override
            public void onResponse(Call<List<FoodOrderItem>> call, Response<List<FoodOrderItem>> response) {
                if(response.code() == 200){
                    responseList = (ArrayList<FoodOrderItem>) response.body();
                    for(FoodOrderItem item:responseList){
                        if(item.getDishTypeId() == 0){
                            foodOrderItemArrayList.add(0,new FoodOrderItem(item.getDishId(), item.getTableId(), item.getDishName(), item.getQuantityOrder(), ApiClient.BASE_URL +"Image/"+item.getImage(), 0, item.getOrderDetailId()));
                        }

                    }
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(KitchenDrink.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FoodOrderItem>> call, Throwable t) {
                Toast.makeText(KitchenDrink.this, "Có lỗi xảy ra. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                Log.e("failure: ", t.toString());
            }
        });
    }
}
