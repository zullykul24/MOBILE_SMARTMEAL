package com.example.myapplication.activities;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
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
import com.example.myapplication.adapters.FoodOrderItemAdapter;
import com.example.myapplication.adapters.KitchenFoodItemAdapter;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;
import com.example.myapplication.models.FoodOrderItem;
import com.example.myapplication.models.MenuFoodItem;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KitchenFood extends AppCompatActivity {
    ListView kitchenOrderedFoodListView;
    ImageButton backToMain;
    ArrayList<FoodOrderItem> foodOrderItemArrayList, responseList;
    KitchenFoodItemAdapter adapter;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    HubConnection hubConnection, hubFoodReady;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_food);
        kitchenOrderedFoodListView = findViewById(R.id.ordered_food_listview);
        backToMain = findViewById(R.id.img_back_btn_from_kitchen_ordered);
        hubConnection = HubConnectionBuilder.create(ApiClient.BASE_URL +"orderFoodHub").build();
        hubFoodReady = HubConnectionBuilder.create(ApiClient.BASE_URL + "foodReadyHub").build();
        hubConnection.start();
        hubFoodReady.start();


        // click to back to main activity
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /////

        foodOrderItemArrayList = new ArrayList<>();
        ///
        // call api to get list orderdetails ordered
        API_GetListOrdered();

        //
        adapter = new KitchenFoodItemAdapter(KitchenFood.this, R.layout.item_kitchen_ordered_food,foodOrderItemArrayList);
        kitchenOrderedFoodListView.setAdapter(adapter);

        // nh???n signalr confirm ordered food
        hubConnection.on("ConfirmOrderedFood", (msg) ->{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        foodOrderItemArrayList.clear();
                        responseList.clear();
                        API_GetListOrdered();
                    }
                });

            Log.e("OrderedFoodReceivedmsg",msg+"");
        }, Integer.class);

        hubFoodReady.on("ConfirmFoodReady", (msg) ->{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    foodOrderItemArrayList.clear();
                    responseList.clear();
                    API_GetListOrdered();
                }
            });

            Log.e("ReadyFoodmsg",msg+"");
        }, Integer.class);


        kitchenOrderedFoodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   ShowDialog(position);
            }
        });

    }


    private void ShowDialog(final int position){
        builder = new AlertDialog.Builder(KitchenFood.this);
        builder.setMessage("M??n ???? s???n s??ng?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // Toast.makeText(KitchenFood.this, foodOrderItemArrayList.get(position).getDishName(), Toast.LENGTH_LONG).show();
                API_PutDishReady(foodOrderItemArrayList.get(position).getOrderDetailId());
            }
        });
        builder.setNegativeButton("HU???", new DialogInterface.OnClickListener() {
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
                    try{
                        hubFoodReady.send("ConfirmFoodReady",0);
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                    /////
                } else {
                    Toast.makeText(KitchenFood.this, "C?? l???i x???y ra.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(KitchenFood.this, "C?? l???i x???y ra. Vui l??ng th??? l???i.", Toast.LENGTH_LONG).show();
                Log.e("PutDishReady failed: ", t.toString());
            }
        });
        new HubConnectionTask().execute(hubFoodReady);
    }


    private void API_GetListOrdered() {
        ApiClient.getApiClient().create(ApiInterface.class).getListOrdered().enqueue(new Callback<List<FoodOrderItem>>() {
            @Override
            public void onResponse(Call<List<FoodOrderItem>> call, Response<List<FoodOrderItem>> response) {
                if(response.code() == 200){
                    responseList = (ArrayList<FoodOrderItem>) response.body();
                    for(FoodOrderItem item:responseList){
                        if(item.getDishTypeId() == 1){
                            foodOrderItemArrayList.add(0,new FoodOrderItem(item.getDishId(), item.getTableId(), item.getDishName(), item.getQuantityOrder(),
                                    ApiClient.BASE_URL +"Image/"+item.getImage(), 1, item.getOrderDetailId()));
                        }

                    }
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(KitchenFood.this, "C?? l???i x???y ra", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FoodOrderItem>> call, Throwable t) {
                Toast.makeText(KitchenFood.this, "C?? l???i x???y ra. Vui l??ng th??? l???i", Toast.LENGTH_SHORT).show();
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
