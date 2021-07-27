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
import com.example.myapplication.adapters.CountByUserItemAdapter;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;
import com.example.myapplication.models.CountByDishNameItem;
import com.example.myapplication.models.CountByUserItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountByUserName extends AppCompatActivity {
    ListView listView;
    ImageButton backBtn;
    Button okBtn;
    ArrayList<CountByUserItem> arrayList;
    ArrayList<CountByUserItem> responseList;
    CountByUserItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_by_user_name);
        backBtn = findViewById(R.id.count_by_user_backtohome_btn);
        listView = findViewById(R.id.listview_count_by_user);
        okBtn = findViewById(R.id.cbu_btn_ok);
        arrayList = new ArrayList<>();
        adapter = new CountByUserItemAdapter(this, R.layout.item_count_by_user, arrayList);
        listView.setAdapter(adapter);

        //add data
        API_GetCountByDishName();

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
        ApiClient.getApiClient().create(ApiInterface.class).getCountByUser().enqueue(new Callback<List<CountByUserItem>>() {
            @Override
            public void onResponse(Call<List<CountByUserItem>> call, Response<List<CountByUserItem>> response) {
                if(response.code() == 200){
                    Log.e("get count by user: ", "ok");
                    responseList = (ArrayList<CountByUserItem>) response.body();
                    for(int i=0; i<responseList.size();i++){
                        arrayList.add(new CountByUserItem(
                                i+1,
                                responseList.get(i).getFullname(),
                                responseList.get(i).getTotaldishes()
                        ));
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("get count by user: ", "err");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CountByUserName.this, "Lấy dữ liệu thất bại",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<CountByUserItem>> call, Throwable t) {
                Log.e("get count by user failed: ", t.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CountByUserName.this, "Lấy dữ liệu thất bại",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}