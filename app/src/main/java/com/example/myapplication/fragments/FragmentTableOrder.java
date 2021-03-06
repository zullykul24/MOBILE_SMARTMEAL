package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Config;
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
    HubConnection hubConnection, hubChangeState;
    private String COLOR_FILLED;
    private String COLOR_BOOKED;
    private String COLOR_EMPTY;
    int setBooked = 0;
    int setEmpty = 0;
    private ArrayList<Table> responseList;
    ArrayList<Table> tableArrayList;
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.set_table_status, menu);
        menu.setHeaderTitle("?????t tr???ng th??i");
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
        hubChangeState = HubConnectionBuilder.create(ApiClient.BASE_URL +"changeTableStateHub").build();
        hubChangeState.start();
        hubConnection.start();
        Account account = DataLocalManager.getLoggedinAccount();
        gridViewTable = (GridView) rootView.findViewById(R.id.gridViewTable);
        tableArrayList = new ArrayList<>();
        tableItemAdapter = new TableItemAdapter(getContext(), R.layout.table_item, tableArrayList);
        API_GetListTables();
        tableItemAdapter.notifyDataSetChanged();
        /////

        hubChangeState.on("ReceiveTableState", (tableId, tag)->{
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(tag == 0){
                        tableArrayList.get(tableId-1).setStatus(0);
                        tableArrayList.get(tableId-1).setColor(COLOR_EMPTY);
                        tableItemAdapter.notifyDataSetChanged();
                    } else {
                        tableArrayList.get(tableId-1).setStatus(-1);
                        tableArrayList.get(tableId-1).setColor(COLOR_BOOKED);
                        tableItemAdapter.notifyDataSetChanged();
                    }
                }
            });
                },Integer.class, Integer.class);
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
                // ch???nh s???a ch??? n??y ????? tr???ng th??i n??o c??ng c?? th??? v??o ch???nh s???a m??n ??n
                //if(status.equals("Empty")) {
                Intent intentToOrder = new Intent(getActivity(), Order.class);
               // intentToOrder.putExtra("T??n b??n", tableArrayList.get(position).getName());
                intentToOrder.putExtra("Table_id", tableArrayList.get(position).getTableId());
                intentToOrder.putExtra("Table_status", tableArrayList.get(position).getStatus());
                intentToOrder.putExtra("accountUsername",  account.getUsername());
                launcher.launch(intentToOrder);
                // }
            }
        });
        registerForContextMenu(gridViewTable);
        return rootView;
    }






    private void API_GetListTables(){
        ApiClient.getApiClient().create(ApiInterface.class).getListTable().enqueue(new Callback<List<Table>>() {
            @Override
            public void onResponse(Call<List<Table>> call, Response<List<Table>> response) {
                responseList = (ArrayList<Table>) response.body();
                for (Table i:responseList){
                    /// th??m t???t c??? c??c m??n t??? response v??o menuItemArrayList
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
                    setEmpty = 1;

                    Toast.makeText(getContext(), "B??n tr???ng th??nh c??ng", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "???? c?? l???i x???y ra. Vui l??ng th??? l???i.", Toast.LENGTH_SHORT).show();
                }
                if(setEmpty == 1){
                    try {
                        hubChangeState.send("ChangeTableState", info.position+1,0);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "???? c?? l???i x???y ra.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void APi_UpdateTableStatusBooked(AdapterView.AdapterContextMenuInfo info){
        ApiClient.getApiClient().create(ApiInterface.class).updateTableStatus(String.valueOf(info.position+1), "-1").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("rspcode", response.code()+"");
                if(response.code() == 200){
                    setBooked = 1;

                    Toast.makeText(getContext(), "?????t tr?????c th??nh c??ng", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "???? c?? l???i x???y ra. Vui l??ng th??? l???i.", Toast.LENGTH_SHORT).show();
                }
                if(setBooked == 1){
                    try {
                        hubChangeState.send("ChangeTableState", info.position+1,-1);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "???? c?? l???i x???y ra.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Config.ORDER_STATUS_CODE){
                        Intent data = result.getData();
                        int status = data.getIntExtra("orderStatus", 0);
                        Log.e("intent data: ",  status +"");
                        Toast.makeText(getContext(), Config.ORDER_STATUS[status], Toast.LENGTH_SHORT).show();
                    }
                }
            });

    class HubConnectionTask extends AsyncTask<HubConnection, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(HubConnection... hubConnections) {
            HubConnection hubConnection1 = hubConnections[0];
            hubConnection1.start().blockingAwait();
            return null;
        }
    }
}

