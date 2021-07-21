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

public class PayBill extends AppCompatActivity {
    int tableId;
    TextView cashierName, tableIdText;
    TextView paymentId;
    TextView date_paymented;
    TextView totalPriceText;
    TextView tablename;
    Button btn_pay;
    ImageButton backToHome;
    int orderId;
    int totalPrice = 0;
    ListView listViewPayBill;
    ArrayList<PayBillItem> payBillItemArrayList;
    ArrayList<FoodOrderItem> responseList;
    PayBillItemAdapter payBillItemAdapter;

    long millis = System.currentTimeMillis();
    java.sql.Date date =new java.sql.Date(millis);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paybill);
        totalPriceText = (TextView)findViewById(R.id.total_price);
        tableIdText = (TextView)findViewById(R.id.tableId);
        cashierName = (TextView) findViewById(R.id.cashier_name);
        paymentId = (TextView) findViewById(R.id.paymentId);
        backToHome = (ImageButton)findViewById(R.id.paybill_backtohome_btn);
        date_paymented = (TextView) findViewById(R.id.date_paymented);
        tablename = (TextView)  findViewById(R.id.text_paybill_table);
        btn_pay = (Button) findViewById(R.id.btn_thanh_toan);
        listViewPayBill = (ListView)findViewById(R.id.listview_pay_bill);
        tableId = getIntent().getIntExtra("tableId", 0);
        orderId = getIntent().getIntExtra("orderId", 0);
        tableIdText.setText(tableId+"");
        paymentId.setText(orderId + "");

        int cashierId = DataLocalManager.getLoggedinAccount().getAccountId();

        cashierName.setText(DataLocalManager.getLoggedinAccount().getFullName());

////        // date payment
        date_paymented.setText(""+ date);
        payBillItemArrayList = new ArrayList<>();
        tablename.setText("Bàn số "+ tableId);
        payBillItemAdapter = new PayBillItemAdapter(PayBill.this, R.layout.item_paybill, payBillItemArrayList);
        listViewPayBill.setAdapter(payBillItemAdapter);

        API_GetOrderDetailsPayment(tableId);



        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /// SET TRẠNG THÁI ĐÃ THANH TOÁN
                API_ConfirmPayment(tableId, cashierId);
                API_ChangeOrderStatusToBePaid(orderId);
                API_UpdateTableStatus(String.valueOf(tableId), "0");
                finish();

            }
        });
        Log.d("checkdate", ""+ date);

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void API_UpdateTableStatus(String tableID, String status){
        ApiClient.getApiClient().create(ApiInterface.class).updateTableStatus(String.valueOf(tableID), status).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    Log.e("update table status to be empty: ", "ok");
                } else {
                    Log.e("update table status to be empty: ", "failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("update table status to be empty failed ", t.toString());
            }
        });
    }

    private void API_ChangeOrderStatusToBePaid(int orderId) {
        ApiClient.getApiClient().create(ApiInterface.class).changeOrderStatusToBePaid(orderId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    Log.e("change order status", "ok");
                } else {
                    Log.e("change order status", "failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("change order status failed: ", t.toString());
            }
        });
    }

    private void API_ConfirmPayment(int tableId0, int cashierId) {
        ApiClient.getApiClient().create(ApiInterface.class).confirmPayment(String.valueOf(tableId0), String.valueOf(cashierId)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    Log.e("confirmed", "OK");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PayBill.this, "Xác nhận thanh toán bàn "+String.valueOf(tableId0)+"thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PayBill.this, "Xác nhận thanh toán thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("confirm payment err: ", t.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PayBill.this, "Có lỗi xảy ra. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                    }
                });
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
                        totalPrice = totalPrice + responseList.get(i).getQuantityOrder() * responseList.get(i).getPrice();

                    }
                    DecimalFormat df= new DecimalFormat("###,###,###");
                    String priceString = df.format(totalPrice);
                    totalPriceText.setText(priceString);

                    payBillItemAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(PayBill.this, "Có lỗi xảy ra.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<FoodOrderItem>> call, Throwable t) {
                Toast.makeText(PayBill.this, "Không tải được hoá đơn. Vui lòng thử lại sau.",
                        Toast.LENGTH_LONG).show();
                Log.e("get orderdetails payment failed: ", t.toString());
            }
        });
    }

}
