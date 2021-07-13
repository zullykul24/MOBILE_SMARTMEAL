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
import com.example.myapplication.adapters.PaymentTableItemAdapter;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;
import com.example.myapplication.models.CreateOrder;
import com.example.myapplication.models.PaymentTableItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Payment extends AppCompatActivity {
    ListView listViewPaymentTable;
    ImageButton backToHomeBtn;
    ArrayList<CreateOrder> paymentTableArrayList, responseList;
    PaymentTableItemAdapter paymentTableAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        paymentTableArrayList = new ArrayList<>();
        listViewPaymentTable = findViewById(R.id.list_payment_item);

        backToHomeBtn = (ImageButton)findViewById(R.id.payment_backtohome_btn);
        backToHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        API_GetPaymentTables();
        paymentTableAdapter = new PaymentTableItemAdapter(this, R.layout.item_payment, paymentTableArrayList);
        listViewPaymentTable.setAdapter(paymentTableAdapter);
        listViewPaymentTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentToPayBill = new Intent(Payment.this, PayBill.class);
                intentToPayBill.putExtra("orderId", paymentTableArrayList.get(position).getOrderId());
                intentToPayBill.putExtra("tableId", paymentTableArrayList.get(position).getTableId());
                startActivity(intentToPayBill);
            }
        });
    }

    private void API_GetPaymentTables() {
        ApiClient.getApiClient().create(ApiInterface.class).getPaymentTables().enqueue(new Callback<List<CreateOrder>>() {
            @Override
            public void onResponse(Call<List<CreateOrder>> call, Response<List<CreateOrder>> response) {
                if(response.code() == 200){
                    responseList = (ArrayList<CreateOrder>) response.body();
                    for (CreateOrder i:responseList){
                        if(i.getStatus() == 0) paymentTableArrayList.add(new CreateOrder(i));
                        paymentTableAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(Payment.this, "Có lỗi xảy ra", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<CreateOrder>> call, Throwable t) {
                Toast.makeText(Payment.this, "Lấy danh sách bàn thất bại.", Toast.LENGTH_LONG).show();
                Log.e("get payment tables failed: ", t.toString());
            }
        });
    }


}