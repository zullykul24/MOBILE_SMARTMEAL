package com.example.myapplication.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.myapplication.R;
import com.example.myapplication.adapters.FoodOrderBookedItemAdapter;
import com.example.myapplication.adapters.FoodOrderItemAdapter;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;
import com.example.myapplication.data_local.DataLocalManager;
import com.example.myapplication.fragments.FragmentTableOrder;
import com.example.myapplication.models.CreateOrder;
import com.example.myapplication.models.FoodOrderItem;
import com.example.myapplication.models.MenuFoodItem;
import com.example.myapplication.models.Table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Order extends AppCompatActivity {
    TextView tableName;
    Button themMonBtn;
    SwipeMenuListView listViewChosenFood;
    ListView bookedListView;
    Button btn_ok ;
    Button btn_cancel ;
    ImageButton backToFragmentTableOrder;
    ArrayList<FoodOrderItem> arrayListChosenFood, arrayListPost;
    ArrayList<FoodOrderItem> arrayListBooked, responseList;

    FoodOrderItemAdapter adapterChosenFood;
    FoodOrderBookedItemAdapter adapterBooked;
    long millis=System.currentTimeMillis();
    java.sql.Date date=new java.sql.Date(millis);
    int table_Id;
    int table_Status;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        table_Id = getIntent().getIntExtra("Table_id", 1);
        tableName = (TextView)findViewById(R.id.nameOfIntentedTable);
        themMonBtn = (Button)findViewById(R.id.themMonBtn);
        backToFragmentTableOrder = (ImageButton)findViewById(R.id.back_to_fragment_table_order);
        table_Status = getIntent().getIntExtra("Table_status", 0);
        // là cái thanh cuộn các món ở dưới.
        listViewChosenFood = (SwipeMenuListView) findViewById(R.id.listViewChosenFood);
        bookedListView = (ListView) findViewById(R.id.bookedList);
        View footer = getLayoutInflater().inflate(R.layout.footer, null);

        // add thêm cái footer ghi chú và button OK
        listViewChosenFood.addFooterView(footer);
        btn_ok = (Button) findViewById(R.id.btn_ok_order);
        btn_cancel = (Button) findViewById(R.id.btn_cancel_order);


        btn_ok = (Button)findViewById(R.id.btn_ok_order);
        btn_cancel = (Button) findViewById(R.id.btn_cancel_order);


        //test
        arrayListBooked = new ArrayList<>();
        if(table_Status == 1){
            ApiClient.getApiClient().create(ApiInterface.class).getListBookedByTable(table_Id).enqueue(new Callback<List<FoodOrderItem>>() {
                @Override
                public void onResponse(Call<List<FoodOrderItem>> call, Response<List<FoodOrderItem>> response) {
                    responseList = (ArrayList<FoodOrderItem>) response.body();
                    Log.e("list size", responseList.size()+"");
                    for (FoodOrderItem i:responseList){
                        /// thêm tất cả các món từ response vào list booked
                        arrayListBooked.add(new FoodOrderItem(i.getDishId(), i.getDishName(), i.getPrice(),i.getDishTypeId(), ApiClient.BASE_URL +"Image/" + i.getImage(), i.getQuantityOrder()));
                    }
                    Log.e("Get list booked status:", "Success");
                    Log.e("get status", response+"");
                    adapterBooked.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<List<FoodOrderItem>> call, Throwable t) {
                    Log.e("Get list booked status:", "Failed"+ t);
                }
            });
        }



        adapterBooked = new FoodOrderBookedItemAdapter(Order.this, R.layout.food_order_booked_item, arrayListBooked);
        bookedListView.setAdapter(adapterBooked);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!arrayListChosenFood.isEmpty()){
                    if(table_Status == 0 || table_Status == -1 ){

                        Table table = new Table(0,0,1);

            /// set status to 1 (is being served)
                        ApiClient.getApiClient().create(ApiInterface.class).updateTableStatus(String.valueOf(table_Id), table).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.code() == 200){
                                   // hubConnection.send("ChangeTableState",table_Id - 1, 0);
                                    Toast.makeText(Order.this, "Đặt món thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Order.this, "Đã có lỗi xảy ra. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(Order.this, "Đã có lỗi xảy ra.", Toast.LENGTH_SHORT).show();
                            }
                        });


                        //// create new order
                        CreateOrder order = new CreateOrder(0,table_Id,0);
                        ApiClient.getApiClient().create(ApiInterface.class).createOrder(order).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    if ((response.code() == 200) && response.body().string().equals("1")){
                                        Log.e("Create order status :", "Ok");
                                    }
                                    else {
                                        Log.e("Create order status :", "Not Ok with code" + response.code());
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e("Create order status :", "Not Ok with throw" + t.toString());
                            }
                        });

                    }

                    //just post necessary info
                    for(FoodOrderItem item:arrayListChosenFood){
                        arrayListPost.add(new FoodOrderItem(table_Id, item.getDishId(), DataLocalManager.getLoggedinAccount().getAccountId(), item.getQuantityOrder()));
                    }
                    ///post new orderdetails
                    ApiClient.getApiClient().create(ApiInterface.class).postOrder(arrayListPost).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if(response.code() == 200 && response.body().string().equals("1")){
                                    Log.e("post order status: ", "ok");
                                }
                                else {
                                    Log.e("post order status: ", "err with code: "+response.code());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("post order failed: ", t+"");
                        }
                    });
                }


                // intent back to fragment table order
                Intent intent = new Intent(Order.this, FragmentTableOrder.class);
                intent.putExtra("banId", table_Id);
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

        tableName.setText("Bàn số "+ table_Id);

        MenuFoodItem v = (MenuFoodItem) intent.getSerializableExtra("abc");

        arrayListChosenFood = new ArrayList<>();
        arrayListPost = new ArrayList<>();

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
        setDynamicHeight(bookedListView);
        setDynamicHeight(listViewChosenFood);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 999) {
            if (resultCode == -1) {
                MenuFoodItem foodItem = (MenuFoodItem) data.getSerializableExtra("abc");
                arrayListChosenFood.add(new FoodOrderItem(foodItem.getDishId(), foodItem.getDishName(),foodItem.getPrice(),  foodItem.getDishTypeId(), foodItem.getImage(), 1));
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
                        Toast.makeText(getApplicationContext(),arrayListChosenFood.get(position).getDishName(),Toast.LENGTH_SHORT).show();
                        arrayListChosenFood.remove(position);
                        adapterChosenFood.notifyDataSetChanged();
                        // false : close the menu; true : not close the menu
                        return false;
                    }
                });
                setDynamicHeight(listViewChosenFood);



            }
        }
    }
    /**
     * Set listview height based on listview children
     *
     * @param listView
     */
    public static void setDynamicHeight(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        //check adapter if null
        if (adapter == null) {
            return;
        }
        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = height + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();
    }
}