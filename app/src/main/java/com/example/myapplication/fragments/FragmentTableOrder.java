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
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.activities.Order;
import com.example.myapplication.adapters.TableItemAdapter;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.models.Account;
import com.example.myapplication.models.MenuFoodItem;
import com.example.myapplication.models.TableItem;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

import java.util.ArrayList;

public class FragmentTableOrder extends Fragment {
    TextView tableTitle;
    GridView gridViewTable;
    TableItemAdapter tableItemAdapter;
    HubConnection hubConnection;
    private String COLOR_FILLED;
    private String COLOR_BOOKED;
    private String COLOR_EMPTY;


    ArrayList<TableItem> tableItemArrayList;
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
                if (tableItemArrayList.get(info.position).getStatus() == 0) {
                    /*
                    database.QueryData("update group_table set status = 'Booked'  where tableId = " + tableItemArrayList.get(info.position).getId() + ";");
                    */
                    ////


                    hubConnection.send("ChangeTableState",info.position, -1);
                    Toast.makeText(getContext(), "Đặt trước thành công", Toast.LENGTH_SHORT).show();
                    ///why is this toast not shown?
                    return true;
                }
                else return false;
                // ??? : sau nay se phai sua cai nayf
            case R.id.status_empty:
                if (tableItemArrayList.get(info.position).getStatus() == -1) {
                    /*
                    database.QueryData("update group_table set status = 'Empty'  where tableId = " + tableItemArrayList.get(info.position).getId() + ";");
                    */

                    hubConnection.send("ChangeTableState",info.position, 0);
                    Toast.makeText(getContext(), "Bàn trống thành công", Toast.LENGTH_SHORT).show();
                    //why is this toast not shown?
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
        hubConnection = HubConnectionBuilder.create(ApiClient.BASE_URL +"changeTableStateHub").build();
        hubConnection.start();
        Account account = (Account) getArguments().get("Account_obj");




        gridViewTable = (GridView) rootView.findViewById(R.id.gridViewTable);
        tableItemArrayList = new ArrayList<>();

        tableItemArrayList.add(new TableItem(1));
        tableItemArrayList.add(new TableItem(2));
        tableItemArrayList.add(new TableItem(3));
        tableItemArrayList.add(new TableItem(4));
        tableItemArrayList.add(new TableItem(5,1));
        tableItemArrayList.add(new TableItem(6,-1));
        tableItemArrayList.add(new TableItem(7,1));
        tableItemArrayList.add(new TableItem(8,-1));
        tableItemArrayList.add(new TableItem(9));
        tableItemArrayList.add(new TableItem(10));


        for(TableItem i:tableItemArrayList){
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
        tableItemAdapter = new TableItemAdapter(getContext(), R.layout.table_item,tableItemArrayList );
        tableItemAdapter.notifyDataSetChanged();
        gridViewTable.setAdapter(tableItemAdapter);
        /////
        hubConnection.on("ReceiveTableState", (tableId, tag) ->{
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(tag == 0){
                        tableItemArrayList.get(tableId).setStatus(0);
                        tableItemArrayList.get(tableId).setColor(COLOR_BOOKED);
                    }
                    else if(tag == -1)
                    {
                        tableItemArrayList.get(tableId).setStatus(-1);
                        tableItemArrayList.get(tableId).setColor(COLOR_EMPTY);
                    }
                    else {
                        tableItemArrayList.get(tableId).setStatus(1);
                        tableItemArrayList.get(tableId).setColor(COLOR_FILLED);
                    }
                    tableItemAdapter.notifyDataSetChanged();
                }
            });

            Log.e("ReceiveTableStateMsg",tableId +""+ tag);
        },Integer.class, Integer.class);
        ////
        gridViewTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getActivity().getApplicationContext(), tableItemArrayList.get(position).getName(), Toast.LENGTH_SHORT).show();
                int status = tableItemArrayList.get(position).getStatus();
                // chỉnh sửa chỗ này để trạng thái nào cũng có thể vào chỉnh sửa món ăn
                //if(status.equals("Empty")) {
                Intent intentToOrder = new Intent(getActivity(), Order.class);
                intentToOrder.putExtra("Tên bàn", tableItemArrayList.get(position).getName());
                intentToOrder.putExtra("Bàn id", tableItemArrayList.get(position).getId());
                intentToOrder.putExtra("Bàn Status", tableItemArrayList.get(position).getStatus());
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
            for(TableItem i:tableItemArrayList){
                if(i.getId() == data.getIntExtra("banId", 1)){
                    //i.setColor(COLOR_FILLED);
                    hubConnection.send("ChangeTableState",i.getId() -1, 1);
                }
            }
            ///reload fragment
           // FragmentTransaction ft = getFragmentManager().beginTransaction();
           // ft.replace(R.id.fragment_container,new FragmentTableOrder()).addToBackStack(null).commit();
        }
    }
}