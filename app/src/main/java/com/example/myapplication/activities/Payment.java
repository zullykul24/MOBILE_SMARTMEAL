package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapters.PaymentTableItemAdapter;
import com.example.myapplication.models.PaymentTableItem;

import java.util.ArrayList;

public class Payment extends AppCompatActivity {
    int REQUEST_EXIT = 2818;
    ListView listViewPaymentTable;
    ImageButton backToHomeBtn;
    ArrayList<PaymentTableItem> paymentTableItemArrayList;
    PaymentTableItemAdapter paymentTableItemAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        paymentTableItemArrayList = new ArrayList<>();
        listViewPaymentTable = findViewById(R.id.list_payment_item);
        Load();

        backToHomeBtn = (ImageButton)findViewById(R.id.payment_backtohome_btn);
        backToHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToHome = new Intent(Payment.this, MainActivity.class);
                setResult(65, intentToHome);
                finish();
            }
        });
        listViewPaymentTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentToPayBill = new Intent(Payment.this, PayBill.class);
                intentToPayBill.putExtra("BanTen", paymentTableItemArrayList.get(position).getTableName());
                intentToPayBill.putExtra("accountUsername", getIntent().getIntExtra("accountUsername", 1));
                startActivityForResult(intentToPayBill, REQUEST_EXIT);
            }
        });
    }
    // lấy tên các bàn mà đang được sử dụng
    private void Load() {
        paymentTableItemArrayList.clear();
       /* Cursor cursor = database.getData("select * from orders where paid = '0' order by orderId desc");
        while (cursor.moveToNext()){
            paymentTableItemArrayList.add(new PaymentTableItem( cursor.getInt(1)));
        }*/
        paymentTableItemAdapter = new PaymentTableItemAdapter(Payment.this,R.layout.item_payment,paymentTableItemArrayList);
        listViewPaymentTable.setAdapter(paymentTableItemAdapter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EXIT) {
            if (resultCode == RESULT_OK) {
                Load();
            }
        }
    }
}