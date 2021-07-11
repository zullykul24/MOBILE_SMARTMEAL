package com.example.myapplication.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapters.FoodOrderItemAdapter;
import com.example.myapplication.adapters.KitchenFoodItemAdapter;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;
import com.example.myapplication.models.FoodOrderItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KitchenFood extends AppCompatActivity {
    ListView kitchenOrderedFoodListView;
    ImageButton backToMain;
    ArrayList<FoodOrderItem> foodOrderItemArrayList, responseList;
    KitchenFoodItemAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_food);
        kitchenOrderedFoodListView = findViewById(R.id.ordered_food_listview);
        backToMain = findViewById(R.id.img_back_btn_from_kitchen_ordered);


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

    }

    private void API_GetListOrdered() {
        ApiClient.getApiClient().create(ApiInterface.class).getListOrdered().enqueue(new Callback<List<FoodOrderItem>>() {
            @Override
            public void onResponse(Call<List<FoodOrderItem>> call, Response<List<FoodOrderItem>> response) {
                if(response.code() == 200){
                    responseList = (ArrayList<FoodOrderItem>) response.body();
                    for(FoodOrderItem item:responseList){
                        if(item.getDishTypeId() == 1){
                            foodOrderItemArrayList.add(new FoodOrderItem(item.getDishId(), item.getTableId(), item.getDishName(), item.getQuantityOrder(), ApiClient.BASE_URL +"Image/"+item.getImage(), 1));
                        }

                    }
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(KitchenFood.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FoodOrderItem>> call, Throwable t) {
                Toast.makeText(KitchenFood.this, "Có lỗi xảy ra. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                Log.e("failure: ", t.toString());
            }
        });
    }
}
