package com.example.myapplication.fragments;

import android.content.Intent;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.myapplication.models.Account;
import com.example.myapplication.data_local.DataLocalManager;
import com.example.myapplication.R;
import com.example.myapplication.activities.MainActivity;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSignIn extends Fragment {

    Button logInBtn;
    CheckBox togglePassword;
    EditText username, password;
    TextView invalidMsgSignIn;
    private List<Account> accountList;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_signin, container, false);
        logInBtn = rootView.findViewById(R.id.loginButton);
        username = (EditText) rootView.findViewById(R.id.usernameEditText);
        password = (EditText) rootView.findViewById(R.id.passwordEditText);
        invalidMsgSignIn = (TextView)rootView.findViewById(R.id.invalidMessageSignIn);
        accountList = new ArrayList<>();


        togglePassword = (CheckBox) rootView.findViewById(R.id.show_hide_password_signin);
        togglePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    if(password.hasFocus()){
                        password.setSelection(password.getText().length());
                    }


                }
                else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    if(password.hasFocus()){
                        password.setSelection(password.getText().length());
                    }

                }
            }
        });

        CheckLogin();

        logInBtn.setOnClickListener(v -> {
            performLogin();
        });
        return  rootView;
    }


    private void performLogin() {
        String stringUsername = username.getText().toString().trim();
        String stringPassword = password.getText().toString().trim();

        ApiClient.getApiClient().create(ApiInterface.class).getAccount(stringUsername).enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {

                accountList = response.body();
                if(accountList == null || accountList.isEmpty()){
                    invalidMsgSignIn.setVisibility(View.VISIBLE);
                    password.getText().clear();

                }
                else if (stringUsername.equals(accountList.get(0).getUsername()) && BCrypt.verifyer().verify(stringPassword.toCharArray(), accountList.get(0).getPassword()).verified) {
                       // intent o day

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    DataLocalManager.setIsLogin(true);
                    DataLocalManager.setLoggedinAccount(accountList.get(0));
                    startActivity(intent);

                } else {
                    invalidMsgSignIn.setVisibility(View.VISIBLE);
                    password.getText().clear();
                    //Toast.makeText(getContext(), "Tai khoan hoac mk sai", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<List<Account>> call, Throwable t) {
                Toast.makeText(getContext(), "Co loi", Toast.LENGTH_LONG).show();
            }
        });

    }
    private void CheckLogin(){
        if(!DataLocalManager.getIsLogin()){
            return;
        } else {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

}