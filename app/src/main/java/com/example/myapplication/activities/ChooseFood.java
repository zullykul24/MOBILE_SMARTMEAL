package com.example.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapters.MenuFoodItemAdapter;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;
import com.example.myapplication.models.MenuFoodItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseFood extends AppCompatActivity {
    ListView listViewFood;
    ImageButton backToOrderActivity;
    MenuFoodItemAdapter menuFoodItemAdapter;
    ArrayList<MenuFoodItem> menuItemArrayList;
    EditText searchText;
    private List<MenuFoodItem> responseList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_foods);
        searchText = (EditText)findViewById(R.id.searchText);
        backToOrderActivity = (ImageButton)findViewById(R.id.back_to_order_activity);

        listViewFood = (ListView)findViewById(R.id.listViewFoodMenu);
        menuItemArrayList = new ArrayList<>();
        /// add món vào list này nè
        ///
        //////chỗ này lấy ảnh test thôi
        ApiClient.getApiClient().create(ApiInterface.class).getListDishes().enqueue(new Callback<List<MenuFoodItem>>() {
            @Override
            public void onResponse(Call<List<MenuFoodItem>> call, Response<List<MenuFoodItem>> response) {
                responseList = (ArrayList<MenuFoodItem>) response.body();
                for (MenuFoodItem i:responseList){
                    /// thêm tất cả các món từ response vào menuItemArrayList
                    menuItemArrayList.add(new MenuFoodItem(i.getDishId(),i.getDishName(), i.getPrice(),i.getDishTypeId(), ApiClient.BASE_URL +"Image/" + i.getImage()));
                }
                Collections.sort(menuItemArrayList, Comparator.comparing(MenuFoodItem::getDishName));
                Log.e("Get list dishes status:", "Success");
                menuFoodItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MenuFoodItem>> call, Throwable t) {
                Log.e("Get list dishes status:", "Failed"+ t);
            }
        });



        menuFoodItemAdapter = new MenuFoodItemAdapter(ChooseFood.this, R.layout.item_menu_food, menuItemArrayList);

        listViewFood.setTextFilterEnabled(true);

        searchText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                ChooseFood.this.menuFoodItemAdapter.getFilter().filter(arg0);


            }

            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            public void afterTextChanged(Editable arg0) {
                ChooseFood.this.menuFoodItemAdapter.getFilter().filter(arg0);

            }
        });

        listViewFood.setAdapter(menuFoodItemAdapter);

        listViewFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentSendFoodToOrderActivity = new Intent(ChooseFood.this, Order.class);
                MenuFoodItem item = new MenuFoodItem(menuItemArrayList.get(position).getDishId(),menuItemArrayList.get(position).getDishName(), menuItemArrayList.get(position).getPrice(),menuItemArrayList.get(position).getDishTypeId(), menuItemArrayList.get(position).getImage());
                intentSendFoodToOrderActivity.putExtra("abc", (Serializable) item);
                setResult(Activity.RESULT_OK, intentSendFoodToOrderActivity);
                finish();
            }
        });
        backToOrderActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBackToOrderActivity = new Intent(ChooseFood.this, Order.class);
                setResult(999, intentBackToOrderActivity);
                finish();
            }
        });


    }
}