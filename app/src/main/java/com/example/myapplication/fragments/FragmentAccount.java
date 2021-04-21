package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Account;
import com.example.myapplication.data_local.DataLocalManager;
import com.example.myapplication.R;
import com.example.myapplication.activities.LoginActivity;

public class FragmentAccount extends Fragment {
    Button logOutBtn;
    String name ="";
    String accountType = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container,false);


        Account account = (Account) getArguments().get("Account_obj");
        name = account.getFullName();
        switch (account.getRoleId()){
            case 1:
                accountType = "Quản lý";
                break;
            case 2:
                accountType = "Thu ngân";
                break;
            case 3:
                accountType = "Nhân viên bếp";
                break;
            case 4:
                accountType = "Phục vụ bàn";
                break;
            default:
                accountType = "Phục vụ bàn";
        }


        ((TextView)rootView.findViewById(R.id.displayName)).setText(name);
        ((TextView)rootView.findViewById(R.id.role_name)).setText(accountType);
        logOutBtn = rootView.findViewById(R.id.logoutBtn);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataLocalManager.setIsLogin(false);
                Intent intentLogOut = new Intent(getActivity(), LoginActivity.class);
                startActivity(intentLogOut);

            }
        });
        return  rootView;
    }


}
