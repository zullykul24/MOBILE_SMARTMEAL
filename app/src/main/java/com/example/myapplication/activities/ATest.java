package com.example.myapplication.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;
import com.example.myapplication.models.MenuFoodItem;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ATest extends AppCompatActivity {
    ArrayList<MenuFoodItem> responseList, menu;

    HubConnection hubConnection;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_signalr);
        Button btn = findViewById(R.id.test_btn);
        TextView txt = findViewById(R.id.test_txt);


        hubConnection = HubConnectionBuilder.create(ApiClient.BASE_URL + "orderFoodHub").build();

        hubConnection.on("ConfirmOrderedFood", (msg)->{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txt.setText(msg.toString());
                }
            });
        }, Integer.class);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API_GetDishes();
            }
        });

    }

    private void API_GetDishes() {
        final int[] count = {0};
        ApiClient.getApiClient().create(ApiInterface.class).getListDishes().enqueue(new Callback<List<MenuFoodItem>>() {
            @Override
            public void onResponse(Call<List<MenuFoodItem>> call, Response<List<MenuFoodItem>> response) {
                responseList = (ArrayList<MenuFoodItem>) response.body();
                for (MenuFoodItem i:responseList){
                    count[0] += 1;
                }
                try {
                    hubConnection.send("ConfirmOrderedFood", count[0]);
                } catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<List<MenuFoodItem>> call, Throwable t) {
                Log.e("Get list dishes status:", "Failed"+ t);
            }
        });
        new HubConnectionTask().execute(hubConnection);
    }


    class HubConnectionTask extends AsyncTask<HubConnection, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(HubConnection... hubConnections) {
            HubConnection hubConnection = hubConnections[0];
            hubConnection.start().blockingAwait();
            return null;
        }
    }
}
