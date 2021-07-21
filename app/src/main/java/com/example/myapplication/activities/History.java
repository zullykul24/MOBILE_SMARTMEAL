package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapters.HistoryItemAdapter;
import com.example.myapplication.adapters.PaymentTableItemAdapter;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;
import com.example.myapplication.models.CreateOrder;
import com.example.myapplication.models.HistoryItem;
import com.example.myapplication.models.PaymentTableItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class History extends AppCompatActivity {
    ListView listViewHistory;
    ImageButton backToHomeBtn;
    ArrayList<HistoryItem> historyItemArrayList, responseList;
    HistoryItemAdapter historyItemAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyItemArrayList = new ArrayList<>();
        listViewHistory = findViewById(R.id.list_history_item);

        backToHomeBtn = (ImageButton)findViewById(R.id.history_backtohome_btn);
        backToHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        API_GetHistoryPayments();
        historyItemAdapter = new HistoryItemAdapter(this, R.layout.item_history, historyItemArrayList);
        listViewHistory.setAdapter(historyItemAdapter);
        listViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentToHistoryDetail = new Intent(History.this, HistoryDetail.class);

                intentToHistoryDetail.putExtra("orderId", historyItemArrayList.get(position).getOrderId());
                intentToHistoryDetail.putExtra("tableId", historyItemArrayList.get(position).getTableId());
                intentToHistoryDetail.putExtra("totalPrice",historyItemArrayList.get(position).getTotalPrice());
                intentToHistoryDetail.putExtra("time", historyItemArrayList.get(position).getTimePayment());
                intentToHistoryDetail.putExtra("cashierName", historyItemArrayList.get(position).getCashierName());

                startActivity(intentToHistoryDetail);
            }
        });
    }

    private void API_GetHistoryPayments() {
        ApiClient.getApiClient().create(ApiInterface.class).getHistory().enqueue(new Callback<List<HistoryItem>>() {
            @Override
            public void onResponse(Call<List<HistoryItem>> call, Response<List<HistoryItem>> response) {
                if(response.code() == 200){
                    responseList = (ArrayList<HistoryItem>) response.body();
                    for(HistoryItem i:responseList){
                        historyItemArrayList.add(0,new HistoryItem(i.getOrderId(),
                                i.getTableId(), i.getTotalPrice(), i.getTimePayment(), i.getCashierName()));
                    }
                    historyItemAdapter.notifyDataSetChanged();
                }
                else {
                    Log.e("get history payment: ",  "failed");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(History.this, "Không tải được lịch sử. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<HistoryItem>> call, Throwable t) {
                Log.e("get history payment failed: ",  t.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(History.this, "Không tải được lịch sử. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}