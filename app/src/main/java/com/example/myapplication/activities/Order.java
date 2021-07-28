package com.example.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.ea.async.Async;
import com.example.myapplication.Config;
import com.example.myapplication.R;

import com.example.myapplication.adapters.FoodOrderItemAdapter;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;
import com.example.myapplication.data_local.DataLocalManager;
import com.example.myapplication.fragments.FragmentTableOrder;
import com.example.myapplication.models.CreateOrder;
import com.example.myapplication.models.FoodOrderItem;
import com.example.myapplication.models.MenuFoodItem;
import com.example.myapplication.models.Table;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ea.async.Async.await;

public class Order extends AppCompatActivity {
    TextView tableName;
    Button themMonBtn;
    SwipeMenuListView listViewFood;
    Button btn_ok;
    Button btn_cancel;
    ImageButton backToFragmentTableOrder;
    ArrayList<FoodOrderItem> arrayListChosenFood, arrayListPost, arrayFood;
    ArrayList<FoodOrderItem>  responseList;
    FoodOrderItemAdapter adapter;

    long millis=System.currentTimeMillis();
    java.sql.Date date=new java.sql.Date(millis);
    int table_Id;
    int table_Status;
    int res;
    int result = 0;
    HubConnection hubConnection;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        table_Id = getIntent().getIntExtra("Table_id", 1);
        tableName = (TextView)findViewById(R.id.nameOfIntentedTable);
        themMonBtn = (Button)findViewById(R.id.themMonBtn);
        backToFragmentTableOrder = (ImageButton)findViewById(R.id.back_to_fragment_table_order);
        table_Status = getIntent().getIntExtra("Table_status", 0);
        // là cái thanh cuộn các món ở dưới.
        listViewFood = (SwipeMenuListView) findViewById(R.id.listViewChosenFood);


        View footer = getLayoutInflater().inflate(R.layout.footer, null);

        // add thêm cái footer ghi chú và button OK
        listViewFood.addFooterView(footer);
        btn_ok = (Button) findViewById(R.id.btn_ok_order);
        btn_cancel = (Button) findViewById(R.id.btn_cancel_order);


        btn_ok = (Button)findViewById(R.id.btn_ok_order);
        btn_cancel = (Button) findViewById(R.id.btn_cancel_order);
        hubConnection = HubConnectionBuilder.create(ApiClient.BASE_URL +"orderFoodHub").build();
        //hubConnectionChangeStatus = HubConnectionBuilder.create(ApiClient.BASE_URL +"changeTableStateHub").build();

        arrayFood = new ArrayList<>();
        arrayListChosenFood = new ArrayList<>();
        arrayListPost = new ArrayList<>();
        if(table_Status == 1){
            API_GetListBookedByTable(table_Id);
        }

        adapter = new FoodOrderItemAdapter(Order.this, R.layout.item_food_order,arrayFood);

        listViewFood.setAdapter(adapter);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!arrayListChosenFood.isEmpty()){
                    for(FoodOrderItem item:arrayFood) {
                        if(item.getIsBooked() == 0)
                        {
                            arrayListPost.add(new FoodOrderItem(table_Id, item.getDishId(),
                                    DataLocalManager.getLoggedinAccount().getAccountId(), item.getQuantityOrder()));
                        }
                    }
                    if(table_Status == 0 || table_Status == -1 ){
                          API_PostTableFirstTime(arrayListPost, table_Id);
                    } else {
                        ///post new orderdetails
                          API_PostNewOrderDetail(arrayListPost, table_Id);
                    }
                } else {
                    finish();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tableName.setText("Bàn số "+ table_Id);


        themMonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToChooseFood = new Intent(Order.this, ChooseFood.class);
                startActivityForResult(intentToChooseFood, 999);
            }
        });
        backToFragmentTableOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    private void API_PostTableFirstTime(ArrayList<FoodOrderItem> arrayListPost, int table_id) {
        Intent intent = new Intent(Order.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("banId", table_id);

        ApiClient.getApiClient().create(ApiInterface.class).postOrderTableFirstTime(arrayListPost, table_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if(response.code() == 200){
                        result = 1;
                        Log.e("postorder1time: ", "ok");

                        Log.e("res", result+"");

                        intent.putExtra("orderStatus", result);
                        setResult(Config.ORDER_STATUS_CODE,intent );
                        finish();
                    }
                    else {
                        Log.e("postorder1time: ", "err with code: "+response.code());
                        intent.putExtra("orderStatus", result);
                        setResult(Config.ORDER_STATUS_CODE,intent );
                        finish();

                    }


                if(result == 1){
                    try {
                        hubConnection.send("ConfirmOrderedFood", table_id);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("postorder1time failed: ", t+"");
                intent.putExtra("orderStatus", result);
                setResult(Config.ORDER_STATUS_CODE,intent );
                finish();


            }
        });
        new HubConnectionTask().execute(hubConnection);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 999) {
            if (resultCode == -1) {
                MenuFoodItem foodItem = (MenuFoodItem) data.getSerializableExtra("itemChosenAtChooseFood");

                arrayFood.add(new FoodOrderItem(foodItem.getDishId(), foodItem.getDishName(),foodItem.getPrice(),  foodItem.getDishTypeId(), foodItem.getImage(), 1,0));

                arrayListChosenFood.add(new FoodOrderItem(foodItem.getDishId(), foodItem.getDishName(),foodItem.getPrice(),  foodItem.getDishTypeId(), foodItem.getImage(), 1,0));

                adapter.notifyDataSetChanged();
                // Thêm trượt để xoá https://github.com/baoyongzhang/SwipeMenuListView
                SwipeMenuCreator creator = new SwipeMenuCreator() {

                    @Override
                    public void create(SwipeMenu menu) {
                        // create "delete" item
                        SwipeMenuItem deleteItem = new SwipeMenuItem(
                                getApplicationContext());
                        // set item background

                        deleteItem.setBackground(new ColorDrawable(Color.rgb(0xe6, 0x00,
                                0x00)));
                        // set item width
                        deleteItem.setWidth(170);
                        // set item title
                        deleteItem.setTitle("Xoá");
                        // set item title fontsize
                        deleteItem.setTitleSize(18);
                        // set item title font color
                        deleteItem.setTitleColor(Color.WHITE);
                        // add to menu
                        menu.addMenuItem(deleteItem);
                    }
                };
                listViewFood.setMenuCreator(creator);
                /// set click cho item ở menu trượt
                listViewFood.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                        Toast.makeText(getApplicationContext(),arrayFood.get(position).getDishName(),Toast.LENGTH_SHORT).show();
                        if(arrayFood.get(position).getIsBooked() == 1) {
                            return false;
                        }
                        arrayFood.remove(position);
                        adapter.notifyDataSetChanged();
                        // false : close the menu; true : not close the menu
                        return false;
                    }
                });

            }
        }
    }

    private void API_GetListBookedByTable(int tableID){
        ApiClient.getApiClient().create(ApiInterface.class).getListBookedByTable(tableID).enqueue(new Callback<List<FoodOrderItem>>() {
            @Override
            public void onResponse(Call<List<FoodOrderItem>> call, Response<List<FoodOrderItem>> response) {
                responseList = (ArrayList<FoodOrderItem>) response.body();
                Log.e("list size", responseList.size()+"");
                for (FoodOrderItem i:responseList){
                    /// thêm tất cả các món từ response vào list booked
                    arrayFood.add(new FoodOrderItem(i.getDishId(), i.getDishName(), i.getPrice(),i.getDishTypeId(), ApiClient.BASE_URL +"Image/" + i.getImage(), i.getQuantityOrder(),1));

                }
                Log.e("Get list booked status:", "Success");
                Log.e("get status", response+"");

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<FoodOrderItem>> call, Throwable t) {
                Log.e("Get list booked status:", "Failed"+ t);
            }
        });
    }


    private void API_PostNewOrderDetail(ArrayList<FoodOrderItem> postArray, int table){
        Intent intent = new Intent(Order.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("banId", table);


        ApiClient.getApiClient().create(ApiInterface.class).postOrder(postArray).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if(response.code() == 200){
                        result = 1;
                        Log.e("post order status: ", "ok");
                        intent.putExtra("orderStatus", result);
                        setResult(Config.ORDER_STATUS_CODE,intent );
                        finish();

                    }
                    else {
                        Log.e("post order status: ", "err with code: "+response.code());
                        intent.putExtra("orderStatus", result);
                        setResult(Config.ORDER_STATUS_CODE,intent );
                        finish();


                    }
                    if(result == 1){
                        try {
                            hubConnection.send("ConfirmOrderedFood", table);
                        }catch (Exception e){
                         e.printStackTrace();
                        }
                    }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("post order failed: ", t+"");
                intent.putExtra("orderStatus", result);
                setResult(Config.ORDER_STATUS_CODE,intent );
                finish();


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