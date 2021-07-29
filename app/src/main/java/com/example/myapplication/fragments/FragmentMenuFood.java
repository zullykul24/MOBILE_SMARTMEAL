package com.example.myapplication.fragments;

import android.os.Build;
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
import androidx.annotation.RequiresApi;
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
        hubConnection.on("ReceiveNewFood", (msg) ->{
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    menuItemArrayList.clear();
                    responseList.clear();
                    API_GetFood();
                }
            });

            Log.e("ReceiveNewFoodmsgInMenu", String.valueOf(msg));
        }, Integer.class);
        menuFoodItemAdapter =  new MenuFoodItemAdapter(getContext(),R.layout.item_menu_food, menuItemArrayList);
        listViewFood.setAdapter(menuFoodItemAdapter);
        //call api get list of dishes
        //
        API_GetFood();




        //


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

        return rootView;
    }

    private void API_GetFood() {
        ApiClient.getApiClient().create(ApiInterface.class).getListDishes().enqueue(new Callback<List<MenuFoodItem>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<MenuFoodItem>> call, Response<List<MenuFoodItem>> response) {
                responseList = (ArrayList<MenuFoodItem>) response.body();
                for (MenuFoodItem i:responseList){
                    /// thêm tất cả các món từ response vào menuItemArrayList
                    menuItemArrayList.add(new MenuFoodItem(i.getDishId(),i.getDishName(), i.getPrice(),i.getDishTypeId(), ApiClient.BASE_URL +"Image/" + i.getImage()));
                }
                Collections.sort(menuItemArrayList);
                Log.e("Get list dishes status:", "Success");
                menuFoodItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MenuFoodItem>> call, Throwable t) {
                Log.e("Get list dishes status:", "Failed"+ t);
            }
        });
    }


}