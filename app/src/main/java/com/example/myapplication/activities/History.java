package com.example.myapplication.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class History extends AppCompatActivity {
    ListView listViewHistory;
    ImageButton backToHomeBtn;
    ArrayList<HistoryItem> historyItemArrayList, responseList;
    HistoryItemAdapter historyItemAdapter;
    Button fromBtn, toBtn, filterBtn;
    private int lastSelectedYear;
    private int lastSelectedMonth;
    private int lastSelectedDayOfMonth;
    String dateFrom, dateTo;
    String dayTo, monthTo, dayFrom, monthFrom;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyItemArrayList = new ArrayList<>();
        listViewHistory = findViewById(R.id.list_history_item);
        fromBtn = findViewById(R.id.from);
        toBtn = findViewById(R.id.to);
        filterBtn = findViewById(R.id.btn_filter_history);

        backToHomeBtn = (ImageButton)findViewById(R.id.history_backtohome_btn);
        backToHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        fromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectDate(0);
            }
        });
        toBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectDate(1);
            }
        });
        // set defaul date - today
        final Calendar c = Calendar.getInstance();
        this.lastSelectedYear = c.get(Calendar.YEAR);
        this.lastSelectedMonth = c.get(Calendar.MONTH);
        this.lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        toBtn.setText(this.lastSelectedDayOfMonth + "-" + (this.lastSelectedMonth + 1)
                + "-" + this.lastSelectedYear);

        fromBtn.setText(1 + "-" + 1
                + "-" + 2020);
        dateFrom = "2020-01-01";

        dayTo = this.lastSelectedDayOfMonth+"";
        monthTo = this.lastSelectedMonth+1+"";


        // chuyển tháng 9->09, ngày 9->09
        if(this.lastSelectedDayOfMonth < 10){
            dayTo = "0"+ dayTo;
        }
        if(this.lastSelectedMonth < 9){
            monthTo = "0" + monthTo;
        }
        dateTo = this.lastSelectedYear + "-" + monthTo + "-" + dayTo;




        Log.d("dateFrom", dateFrom);
        Log.d("dateTo", dateTo);

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseList.clear();
                historyItemArrayList.clear();
                API_GetHistoryPayments(dateFrom, dateTo);
                historyItemAdapter.notifyDataSetChanged();
            }
        });

        ////


        // call api to get history payments

        API_GetHistoryPayments(dateFrom, dateTo);
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
    private void buttonSelectDate(int type) {
        // Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                if(type == 0){
                    fromBtn.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    dayFrom = dayOfMonth + "";
                    monthFrom = monthOfYear + 1 + "";
                    if(monthOfYear<9){
                        monthFrom = "0" + monthFrom;
                    }
                    if(dayOfMonth < 10){
                        dayFrom = "0" + dayFrom;
                    }
                    dateFrom = year + "-" + monthFrom + "-" + dayFrom;
                    Log.d("dateFrom", dateFrom);

                } else {
                    toBtn.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    dayTo = dayOfMonth + "";
                    monthTo = monthOfYear + 1 + "";
                    if(monthOfYear<9){
                        monthTo= "0" + monthTo;
                    }
                    if(dayOfMonth < 10){
                        dayTo = "0" + dayTo;
                    }
                    dateTo = year + "-" + monthTo + "-" + dayTo;
                    Log.d("dateTo", dateTo);
                }


                lastSelectedYear = year;
                lastSelectedMonth = monthOfYear;
                lastSelectedDayOfMonth = dayOfMonth;
            }
        };

        DatePickerDialog datePickerDialog = null;
        datePickerDialog = new DatePickerDialog(this,
                dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
        // Show
        datePickerDialog.show();
    }

    private void API_GetHistoryPayments(String date_from, String date_to) {
        ApiClient.getApiClient().create(ApiInterface.class).getHistoryByDate(date_from, date_to).enqueue(new Callback<List<HistoryItem>>() {
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