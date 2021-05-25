package com.example.myapplication.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.adapters.MenuFoodItemAdapter;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;
import com.example.myapplication.models.MenuFoodItem;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMenuFood extends Fragment {


    MenuFoodItemAdapter menuFoodItemAdapter;
    ArrayList<MenuFoodItem> menuItemArrayList;
    private List<MenuFoodItem> responseList;
    EditText searchText;
    ListView  listViewFood;
    HubConnection hubConnection;
    private MenuFoodItem receivedItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu_food, container, false);
        //khai bao
        searchText = (EditText)rootView.findViewById(R.id.searchText);

        listViewFood = (ListView)rootView.findViewById(R.id.listViewFoodMenu);
        menuItemArrayList = new ArrayList<>();
        hubConnection = HubConnectionBuilder.create(ApiClient.BASE_URL +"addFoodHub").build();

        hubConnection.start();
        //call api get list of dishes
        //
        ApiClient.getApiClient().create(ApiInterface.class).getListDishes().enqueue(new Callback<List<MenuFoodItem>>() {
            @Override
            public void onResponse(Call<List<MenuFoodItem>> call, Response<List<MenuFoodItem>> response) {
                responseList = (ArrayList<MenuFoodItem>) response.body();
                for (MenuFoodItem i:responseList){
                    /// thêm tất cả các món từ response vào menuItemArrayList
                    menuItemArrayList.add(new MenuFoodItem(i.getDishName(), i.getPrice(),i.getDishTypeId(), ApiClient.BASE_URL +"Image/" + i.getImage()));
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



        //
        menuFoodItemAdapter =  new MenuFoodItemAdapter(getContext(),R.layout.item_menu_food, menuItemArrayList);
        listViewFood.setAdapter(menuFoodItemAdapter);

        listViewFood.setTextFilterEnabled(true);

        searchText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                menuFoodItemAdapter.getFilter().filter(arg0);


            }

            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            public void afterTextChanged(Editable arg0) {
                menuFoodItemAdapter.getFilter().filter(arg0);

            }
        });
        menuFoodItemAdapter.notifyDataSetChanged();
        hubConnection.on("ReceiveNewFood", (foodName, foodPrice, dishType) ->{
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    receivedItem = new MenuFoodItem(foodName,foodPrice, dishType, ApiClient.BASE_URL);
                    menuItemArrayList.add(receivedItem);
                    Collections.sort(menuItemArrayList, Comparator.comparing(MenuFoodItem::getDishName));
                    menuFoodItemAdapter.notifyDataSetChanged();
                }
            });

            Log.e("ReceiveNewFoodmsgInMenu",foodName + foodPrice+""+ dishType+"");
        }, String.class, Integer.class, Integer.class);
        return rootView;
    }
}