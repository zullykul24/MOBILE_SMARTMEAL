package com.example.myapplication.activities;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapters.PayBillItemAdapter;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;
import com.example.myapplication.data_local.DataLocalManager;
import com.example.myapplication.models.FoodOrderItem;
import com.example.myapplication.models.PayBillItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryDetail extends AppCompatActivity {
    int tableId;
    TextView cashierName, tableIdText;
    TextView paymentId;
    TextView date_paymented;
    TextView totalPriceText;
    TextView tablename;
    Button btn_ok;
    ImageButton backToHome;
    int orderId, total_price;
    String time = "";
    String cashier_name = "";

    ListView listViewPayBill;
    ArrayList<PayBillItem> payBillItemArrayList;
    ArrayList<FoodOrderItem> responseList;
    PayBillItemAdapter payBillItemAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        totalPriceText = (TextView)findViewById(R.id.total_price);
        tableIdText = (TextView)findViewById(R.id.tableId);
        cashierName = (TextView) findViewById(R.id.cashier_name);
        paymentId = (TextView) findViewById(R.id.paymentId);
        backToHome = (ImageButton)findViewById(R.id.paybill_backtohome_btn);
        date_paymented = (TextView) findViewById(R.id.date_paymented);
        tablename = (TextView)  findViewById(R.id.text_paybill_table);
        btn_ok= (Button) findViewById(R.id.btn_ok);
        listViewPayBill = (ListView)findViewById(R.id.listview_pay_bill);
        tableId = getIntent().getIntExtra("tableId", 0);
        orderId = getIntent().getIntExtra("orderId", 0);
        total_price = getIntent().getIntExtra("totalPrice", 0);
        time = getIntent().getStringExtra("time");
        cashier_name = getIntent().getStringExtra("cashierName");
        tableIdText.setText(tableId+"");
        paymentId.setText(orderId + "");

        cashierName.setText(cashier_name);

       // date payment
        date_paymented.setText(""+ time);
        payBillItemArrayList = new ArrayList<>();
        tablename.setText("Bàn số "+ tableId);
        payBillItemAdapter = new PayBillItemAdapter(HistoryDetail.this, R.layout.item_paybill, payBillItemArrayList);
        listViewPayBill.setAdapter(payBillItemAdapter);
        DecimalFormat df= new DecimalFormat("###,###,###");
        String priceString = df.format(total_price);
        totalPriceText.setText(priceString);
        API_GetOrderDetailsPayment(orderId);



        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }




    private void API_GetOrderDetailsPayment(int tableDoneId) {
        ApiClient.getApiClient().create(ApiInterface.class).getListDone(tableDoneId).enqueue(new Callback<List<FoodOrderItem>>() {
            @Override
            public void onResponse(Call<List<FoodOrderItem>> call, Response<List<FoodOrderItem>> response) {
                if(response.code() == 200){
                    responseList = (ArrayList<FoodOrderItem>) response.body();
                    for(int i = 0; i< responseList.size(); i++){
                        payBillItemArrayList.add(
                                new PayBillItem(
                                        i + 1,
                                        responseList.get(i).getDishName(),
                                        responseList.get(i).getQuantityOrder(),
                                        responseList.get(i).getPrice()
                                )
                        );


                    }


                    payBillItemAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(HistoryDetail.this, "Có lỗi xảy ra.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<FoodOrderItem>> call, Throwable t) {
                Toast.makeText(HistoryDetail.this, "Không tải được hoá đơn. Vui lòng thử lại sau.",
                        Toast.LENGTH_LONG).show();
                Log.e("get history details failed: ", t.toString());
            }
        });
    }

}
