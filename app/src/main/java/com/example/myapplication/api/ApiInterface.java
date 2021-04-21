package com.example.myapplication.api;

import com.example.myapplication.Account;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


    public interface ApiInterface {

        @GET("Account/{username}")
        Call<List<Account>> getAccount(@Path("username") String username);

        @POST("Account")
        Call<ResponseBody> createAccount(@Body Account account);

    }

