package com.example.myapplication.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapters.KitchenFoodItemAdapter;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;
import com.example.myapplication.models.FoodOrderItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KitchenDrink extends AppCompatActivity {
    ListView kitchenOrderedDrinkListView;
    ImageButton backToMain;
    ArrayList<FoodOrderItem> foodOrderItemArrayList, responseList;
    KitchenFoodItemAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_drink);
        kitchenOrderedDrinkListView = (ListView)findViewById(R.id.ordered_drinklistview);
        backToMain = (ImageButton)findViewById(R.id.img_back_btn_from_kitchen_ordered_drink);
        foodOrderItemArrayList = new ArrayList<>();


        adapter = new KitchenFoodItemAdapter(KitchenDrink.this,  R.layout.item_kitchen_ordered_food, foodOrderItemArrayList);


        // call api to get list orderdetails drink ordered
        API_GetListDrinkOrdered();

        kitchenOrderedDrinkListView.setAdapter(adapter);


        // click to back to main activity
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                            foodOrderItemArrayList.add(new FoodOrderItem(item.getDishId(), item.getTableId(), item.getDishName(), item.getQuantityOrder(), ApiClient.BASE_URL +"Image/"+item.getImage(), 0));
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
