package com.example.myapplication.activities;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapters.PayBillItemAdapter;
import com.example.myapplication.models.PayBillItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PayBill extends AppCompatActivity {
    int tableId;
    TextView staffId;
    TextView paymentId;
    TextView date_paymented;
    TextView totalPriceText;
    TextView tablename;
    Button btn_pay;
    int orderId;
    double total;
    double money_discount=0;
    int voucherId = 0;
    double so_tien =0;

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return this.total;
    }

    long millis = System.currentTimeMillis();
    java.sql.Date date =new java.sql.Date(millis);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paybill);
        ListView listViewPayBill;
        ArrayList<PayBillItem> payBillItemArrayList;
        PayBillItemAdapter payBillItemAdapter;

        totalPriceText = (TextView)findViewById(R.id.total_price);


        staffId = (TextView) findViewById(R.id.idNhanVien);
        paymentId = (TextView) findViewById(R.id.paymentId);
        date_paymented = (TextView) findViewById(R.id.date_paymented);
        tablename = (TextView)  findViewById(R.id.text_paybill_table);
        btn_pay = (Button) findViewById(R.id.btn_thanh_toan);

        total = 0;
        tableId = getIntent().getIntExtra("BanTen", 1);
//        // làm paymentId trước nhá
       /* Cursor cursor1  = database.getData("select max(paymentid) from payments;");
        if(cursor1.getCount()>0){
            while (cursor1.moveToNext()){
                paymentId.setText(""+ (cursor1.getInt(0)+1));
            }
        }else{
            paymentId.setText("1");
        }
        */

////        // date payment
        date_paymented.setText(""+ date);
//
//        // staffId : lấy ra tên nhân viên.
       /* Cursor cursor2 = database.getData("select * from account where accountId = " + getIntent().getIntExtra("accountId", 1) );
        while (cursor2.moveToNext()){
            staffId.setText(cursor2.getString(3));
        }
        */

        listViewPayBill = (ListView)findViewById(R.id.listview_pay_bill);
        payBillItemArrayList = new ArrayList<>();

        tablename.setText("Bàn số "+ tableId);
        /*
        Cursor cursor = database.getData("select dishname, quantityOrder, price, orderdetails.orderId as oId  from orderdetails join orders  on orderdetails.orderId = orders.orderid " +
                " join dish on orderdetails.dishid = dish.dishid " +
                "where oId = (select max(orderId) from orders where tableId = "+tableId+")");
        while(cursor.moveToNext()){
            payBillItemArrayList.add(new PayBillItem(  cursor.getString(0), cursor.getInt(1), cursor.getDouble(2) ));
            orderId = cursor.getInt(3);
        }

         */

        for(int i=0; i< payBillItemArrayList.size();i++){
            payBillItemArrayList.get(i).setSTT(i+1);
            so_tien = so_tien + payBillItemArrayList.get(i).getPriceTotal();
        }
        total = so_tien;

        payBillItemAdapter = new PayBillItemAdapter(PayBill.this, R.layout.item_paybill, payBillItemArrayList);
        listViewPayBill.setAdapter(payBillItemAdapter);

        DecimalFormat df= new DecimalFormat("###,###,###");
        String priceString = String.valueOf(df.format(Double.valueOf(so_tien)));
        totalPriceText.setText(priceString);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /// SET TRẠNG THÁI ĐÃ THANH TOÁN
                /*
                // insert db vao trong co so du lieu
                    database.QueryData("insert into payments values (null, "+getIntent().getIntExtra("accountId", 1)+" , "+tableId+","+orderId+" ,null, "+total+", "+millis+", 'Paid')");
                // update lai cai ban la con trong la oke
                database.QueryData("update group_table set status = 'Empty'  where tableId = "+tableId+";");
                database.QueryData("update orders set paid = 1 where orderId = " + orderId);
                // thoat ra khoi man hinh chinh

                 */
                setResult(RESULT_OK, null);
                finish();
            }
        });
        Log.d("checkdate", ""+ date);

    }

}
