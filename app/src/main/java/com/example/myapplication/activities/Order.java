package com.example.myapplication.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.myapplication.R;
import com.example.myapplication.adapters.FoodOrderItemAdapter;
import com.example.myapplication.fragments.FragmentTableOrder;
import com.example.myapplication.models.FoodOrderItem;
import com.example.myapplication.models.MenuFoodItem;

import java.util.ArrayList;

public class Order extends AppCompatActivity {
    TextView tableName;
    Button themMonBtn;
    SwipeMenuListView listViewChosenFood;
    Button btn_ok ;
    Button btn_cancel ;
    ImageButton backToFragmentTableOrder;
    ArrayList<FoodOrderItem> arrayListChosenFood;
    EditText note;
    FoodOrderItemAdapter adapterChosenFood;
    long millis=System.currentTimeMillis();
    java.sql.Date date=new java.sql.Date(millis);
    int banId;
    String banStatus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        banId = getIntent().getIntExtra("Bàn id", 1);
        tableName = (TextView)findViewById(R.id.nameOfIntentedTable);
        themMonBtn = (Button)findViewById(R.id.themMonBtn);
        backToFragmentTableOrder = (ImageButton)findViewById(R.id.back_to_fragment_table_order);
        banStatus = getIntent().getStringExtra("Bàn Status");
        // là cái thanh cuộn các món ở dưới.
        listViewChosenFood = (SwipeMenuListView) findViewById(R.id.listViewChosenFood);
        View footer = getLayoutInflater().inflate(R.layout.footer, null);
        // add thêm cái footer ghi chú và button OK
        listViewChosenFood.addFooterView(footer);
        btn_ok = (Button) findViewById(R.id.btn_ok_order);
        btn_cancel = (Button) findViewById(R.id.btn_cancel_order);
        note = (EditText) findViewById(R.id.noteFooter);
        if(!banStatus.equals("Empty") && !banStatus.equals("Booked")) {
           /* Cursor cursor = database.getData("Select * from orders where orderId  = (select max(orderId) from orders where tableId = " + banId + ")");
            while (cursor.moveToNext()) {
                note.setText(cursor.getString(2));
            }

            */
        }
        btn_ok = (Button)findViewById(R.id.btn_ok_order);
        btn_cancel = (Button) findViewById(R.id.btn_cancel_order);
        note = (EditText) findViewById(R.id.noteFooter);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(banStatus.equals("Empty") || banStatus.equals("Booked")){
                    if(!arrayListChosenFood.isEmpty()){
                        if(note.getText().toString().trim().length()>0)
                        {
                            //  database.QueryData("insert into orders values(null,"+banId+",'"+note.getText().toString()+"' , 0)");
                        }else {
                            // database.QueryData("insert into orders values(null,"+banId+",null , 0)");
                        }
                        // database.QueryData("update group_table set status = 'Not Empty'  where tableId = " + banId + ";");
                    }
                }
               /* Cursor cursor =  database.getData("Select * from orders where tableId = " + banId + " order by orderId DESC Limit 1 ; ");
                int orderId =0;
                String no = "";
                while (cursor.moveToNext()){
                    orderId = cursor.getInt(0);
                    no = cursor.getString(2);
                }



                for( FoodOrderItem food : arrayListChosenFood){
                        Cursor cursor1 = database.getData("SELECT * from orderdetails where orderId = " + orderId + " and dishId = " + food.getDish_id());
                       // Nếu chưa từng đặt món này
                        if (cursor1.getCount() == 0){
                            database.QueryData("insert into orderdetails values ("+orderId+", "+food.getDish_id()+", "+getIntent().getIntExtra("AccountID", 0)+", "+food.getNumber()+" , "+date+ ")");
                        }
                        else
                        {
                            int quantityOrdered = 0;
                            while (cursor1.moveToNext()){
                                quantityOrdered = cursor1.getInt(3);
                            }
                            quantityOrdered += food.getNumber();
                            database.QueryData("update orderdetails set quantityOrder = " + quantityOrdered + " where dishId = " + food.getDish_id());
                        }
              }

                */

                //database.QueryData("update orders set note = '"+note.getText().toString()+"' where orderId = "+orderId+"");
                Intent intent = new Intent(Order.this, FragmentTableOrder.class);
                intent.putExtra("banId", banId);
                setResult(291,intent );
                finish();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        String nameOfTableSelected = intent.getStringExtra("Tên bàn");
        tableName.setText(nameOfTableSelected);

        MenuFoodItem v = (MenuFoodItem) intent.getSerializableExtra("abc");

        arrayListChosenFood = new ArrayList<>();

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
                Intent intentToFragmentTableOrder = new Intent(Order.this, MainActivity.class);
                setResult(114, intentToFragmentTableOrder);
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 999) {
            if (resultCode == -1) {
                MenuFoodItem foodItem = (MenuFoodItem) data.getSerializableExtra("abc");
                arrayListChosenFood.add(new FoodOrderItem(foodItem.getDishId(), foodItem.getDishName(),foodItem.getPrice(),  foodItem.getDishTypeId(), foodItem.getImage()));
                adapterChosenFood = new FoodOrderItemAdapter(Order.this, R.layout.food_order_item, arrayListChosenFood);
                listViewChosenFood.setAdapter(adapterChosenFood);
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
                listViewChosenFood.setMenuCreator(creator);
                /// set click cho item ở menu trượt
                listViewChosenFood.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                        Toast.makeText(getApplicationContext(),arrayListChosenFood.get(position).getDish_name(),Toast.LENGTH_SHORT).show();
                        arrayListChosenFood.remove(position);
                        adapterChosenFood.notifyDataSetChanged();
                        // false : close the menu; true : not close the menu
                        return false;
                    }
                });


            }
        }
    }
}