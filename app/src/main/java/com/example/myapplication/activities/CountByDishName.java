package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapters.CountByDishNameItemAdapter;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;
import com.example.myapplication.models.CountByDishNameItem;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountByDishName extends AppCompatActivity {
    ListView listView;
    ImageButton backBtn;
    Button okBtn;
    ArrayList<CountByDishNameItem> arrayList, responseList;
    CountByDishNameItemAdapter adapter;
    HubConnection hubConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_by_dish_name);
        backBtn = findViewById(R.id.count_by_dishname_backtohome_btn);
        listView = findViewById(R.id.listview_count_by_dishname);
        okBtn = findViewById(R.id.cbd_btn_ok);
        hubConnection = HubConnectionBuilder.create(ApiClient.BASE_URL +"confirmPaymentHub").build();
        hubConnection.start();
        arrayList = new ArrayList<>();
        adapter = new CountByDishNameItemAdapter(this, R.layout.item_count_by_dishname, arrayList);
        listView.setAdapter(adapter);

        //add data
        API_GetCountByDishName();

        hubConnection.on("ConfirmPayment", (msg)->{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    arrayList.clear();
                    responseList.clear();
                    API_GetCountByDishName();
                }
            });
        }, Integer.class);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void API_GetCountByDishName() {
        ApiClient.getApiClient().create(ApiInterface.class).getCountByDishName().enqueue(new Callback<List<CountByDishNameItem>>() {
            @Override
            public void onResponse(Call<List<CountByDishNameItem>> call, Response<List<CountByDishNameItem>> response) {
                if(response.code() == 200){
                    Log.e("get count: ", "ok");
                    responseList = (ArrayList<CountByDishNameItem>) response.body();
                    for(int i=0; i<responseList.size();i++){
                        arrayList.add(new CountByDishNameItem(
                                i+1,
                                responseList.get(i).getDishName(),
                                responseList.get(i).getSumQuantity()
                        ));
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("get count: ", "err");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CountByDishName.this, "Lấy dữ liệu thất bại",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<CountByDishNameItem>> call, Throwable t) {
                Log.e("get count failed: ", t.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CountByDishName.this, "Lấy dữ liệu thất bại",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}