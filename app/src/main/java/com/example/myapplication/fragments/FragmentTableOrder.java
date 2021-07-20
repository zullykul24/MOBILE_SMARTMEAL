package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.activities.Order;
import com.example.myapplication.adapters.TableItemAdapter;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;
import com.example.myapplication.data_local.DataLocalManager;
import com.example.myapplication.models.Account;
import com.example.myapplication.models.Table;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTableOrder extends Fragment {
    TextView tableTitle;
    GridView gridViewTable;
    TableItemAdapter tableItemAdapter;
    HubConnection hubConnection;
    private String COLOR_FILLED;
    private String COLOR_BOOKED;
    private String COLOR_EMPTY;
    private ArrayList<Table> responseList;
    ArrayList<Table> tableArrayList;
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.set_table_status, menu);
        menu.setHeaderTitle("Đặt trạng thái");
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    public boolean onContextItemSelected(MenuItem item) {
        // below variable info contains clicked item info and it can be null; scroll down to see a fix for it
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()){
            case R.id.status_booked:
                if (tableArrayList.get(info.position).getStatus() == 0) {
                    tableArrayList.get(info.position).setStatus(-1);
                    APi_UpdateTableStatusBooked(info);
                    return true;
                }
                else return false;
            case R.id.status_empty:
                if (tableArrayList.get(info.position).getStatus() == -1) {
                    tableArrayList.get(info.position).setStatus(0);
                    API_UpdateTableStatusEmpty(info);
                    return true;
                }
                else return false;
            default:
                return super.onContextItemSelected(item);
        }

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tableorder, container,false);
        tableTitle = rootView.findViewById(R.id.textViewTableTitle);
        COLOR_FILLED = "#"+Integer.toHexString(ContextCompat.getColor(getActivity(), R.color.colorFilledTable));
        COLOR_BOOKED = "#"+Integer.toHexString(ContextCompat.getColor(getActivity(), R.color.colorBookedTable));
        COLOR_EMPTY = "#"+Integer.toHexString(ContextCompat.getColor(getActivity(), R.color.colorEmptyTable));
        hubConnection = HubConnectionBuilder.create(ApiClient.BASE_URL +"orderFoodHub").build();
        hubConnection.start();


        Account account = DataLocalManager.getLoggedinAccount();
        gridViewTable = (GridView) rootView.findViewById(R.id.gridViewTable);
        tableArrayList = new ArrayList<>();
        tableItemAdapter = new TableItemAdapter(getContext(), R.layout.table_item, tableArrayList);

        API_GetListTables();

        tableItemAdapter.notifyDataSetChanged();


        /////
        hubConnection.on("ConfirmOrderedFood", (tableId) ->{
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                        tableArrayList.get(tableId-1).setStatus(1);
                        tableArrayList.get(tableId-1).setColor(COLOR_FILLED);
                    tableItemAdapter.notifyDataSetChanged();
                }
            });

            Log.e("ConfirmOrderedFood",tableId.toString());
        },Integer.class);
        ////
        gridViewTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getActivity().getApplicationContext(), tableItemArrayList.get(position).getName(), Toast.LENGTH_SHORT).show();
                int status = tableArrayList.get(position).getStatus();
                // chỉnh sửa chỗ này để trạng thái nào cũng có thể vào chỉnh sửa món ăn
                //if(status.equals("Empty")) {
                Intent intentToOrder = new Intent(getActivity(), Order.class);
               // intentToOrder.putExtra("Tên bàn", tableArrayList.get(position).getName());
                intentToOrder.putExtra("Table_id", tableArrayList.get(position).getTableId());
                intentToOrder.putExtra("Table_status", tableArrayList.get(position).getStatus());
                intentToOrder.putExtra("accountUsername",  account.getUsername());
                startActivityForResult(intentToOrder, 114);
                // }
            }
        });
        registerForContextMenu(gridViewTable);
        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==114&& resultCode == 291){
            //WHY THIS TOAST IS NOT SHOWN?
            Toast.makeText(getContext().getApplicationContext(), data.getStringExtra("orderStatus"), Toast.LENGTH_SHORT).show();

        }
    }


    private void API_GetListTables(){
        ApiClient.getApiClient().create(ApiInterface.class).getListTable().enqueue(new Callback<List<Table>>() {
            @Override
            public void onResponse(Call<List<Table>> call, Response<List<Table>> response) {
                responseList = (ArrayList<Table>) response.body();
                for (Table i:responseList){
                    /// thêm tất cả các món từ response vào menuItemArrayList
                    tableArrayList.add(new Table(i.getTableId(),i.getSeatAmount(), i.getStatus()));
                }
                for(Table i: tableArrayList){
                    if(i.getStatus() == 1){
                        i.setColor(COLOR_FILLED);
                    }
                    else if(i.getStatus() == -1){
                        i.setColor(COLOR_BOOKED);
                    }
                    else
                    {
                        i.setColor(COLOR_EMPTY);
                    }
                }
                gridViewTable.setAdapter(tableItemAdapter);

            }

            @Override
            public void onFailure(Call<List<Table>> call, Throwable t) {
                Log.e("Get list tables status:", "Failed"+ t);
            }
        });
    }
    private void API_UpdateTableStatusEmpty(AdapterView.AdapterContextMenuInfo info){
        ApiClient.getApiClient().create(ApiInterface.class).updateTableStatus(String.valueOf(info.position+1), "0").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    hubConnection.send("ChangeTableState",info.position, 0);
                    Toast.makeText(getContext(), "Bàn trống thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Đã có lỗi xảy ra. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Đã có lỗi xảy ra.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void APi_UpdateTableStatusBooked(AdapterView.AdapterContextMenuInfo info){
        ApiClient.getApiClient().create(ApiInterface.class).updateTableStatus(String.valueOf(info.position+1), "-1").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("rspcode", response.code()+"");
                if(response.code() == 200){
                    hubConnection.send("ChangeTableState",info.position, -1);
                    Toast.makeText(getContext(), "Đặt trước thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Đã có lỗi xảy ra. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Đã có lỗi xảy ra.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}