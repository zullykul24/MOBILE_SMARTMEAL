package com.example.myapplication.activities;

import android.content.DialogInterface;
import android.os.AsyncTask;
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

public class WaiterFoodReady extends AppCompatActivity {
    ListView kitchenOrderedFoodListView;
    ImageButton backToMain;
    ArrayList<FoodOrderItem> foodReadyItemArrayList, responseList;
    KitchenFoodItemAdapter adapter;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    HubConnection hubConnection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_food_ready);
        kitchenOrderedFoodListView = findViewById(R.id.ready_food_listview);
        backToMain = findViewById(R.id.img_back_btn_from_ready_food);
        hubConnection = HubConnectionBuilder.create(ApiClient.BASE_URL + "foodDoneHub").build();
        hubConnection.start();


        // click to back to main activity
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /////

        foodReadyItemArrayList = new ArrayList<>();
        ///
        // call api to get list orderdetails ordered
        API_GetListReady();

        //
        adapter = new KitchenFoodItemAdapter(WaiterFoodReady.this, R.layout.item_kitchen_ordered_food,foodReadyItemArrayList);
        kitchenOrderedFoodListView.setAdapter(adapter);

        hubConnection.on("FoodDone", (msg)->{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    foodReadyItemArrayList.clear();
                    responseList.clear();
                    API_GetListReady();
                }
            });
        }, Integer.class);
        //
        kitchenOrderedFoodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowDialog(position);

            }
        });
    }
    private void ShowDialog(final int position){
        builder = new AlertDialog.Builder(WaiterFoodReady.this);
        builder.setMessage("Món đã được phục vụ?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(KitchenFood.this, foodReadyItemArrayList.get(position).getDishName(), Toast.LENGTH_LONG).show();
                API_PutDishServed(foodReadyItemArrayList.get(position).getOrderDetailId());
            }
        });
        builder.setNegativeButton("HUỶ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void API_PutDishServed(int orderDetailId) {
        ApiClient.getApiClient().create(ApiInterface.class).putDishReady(orderDetailId, "2").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    try{
                        hubConnection.send("FoodDone", 0);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    /////
                } else {
                    Toast.makeText(WaiterFoodReady.this, "Có lỗi xảy ra.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(WaiterFoodReady.this, "Có lỗi xảy ra. Vui lòng thử lại.", Toast.LENGTH_LONG).show();
                Log.e("PutDishReady failed: ", t.toString());
            }
        });
        new HubConnectionTask().execute(hubConnection);
    }


    private void API_GetListReady() {
        ApiClient.getApiClient().create(ApiInterface.class).getListReady().enqueue(new Callback<List<FoodOrderItem>>() {
            @Override
            public void onResponse(Call<List<FoodOrderItem>> call, Response<List<FoodOrderItem>> response) {
                if(response.code() == 200){
                    responseList = (ArrayList<FoodOrderItem>) response.body();
                    for(FoodOrderItem item:responseList){
                            foodReadyItemArrayList.add(new FoodOrderItem(item.getDishId(), item.getTableId(), item.getDishName(), item.getQuantityOrder(),
                                    ApiClient.BASE_URL +"Image/"+item.getImage(), 1, item.getOrderDetailId()));
                    }
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(WaiterFoodReady.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FoodOrderItem>> call, Throwable t) {
                Toast.makeText(WaiterFoodReady.this, "Có lỗi xảy ra. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                Log.e("failure: ", t.toString());
            }
        });
    }
    class HubConnectionTask extends AsyncTask<HubConnection, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(HubConnection... hubConnections) {
            HubConnection hubConnection1 = hubConnections[0];
            hubConnection1.start().blockingAwait();
            return null;
        }
    }
}
