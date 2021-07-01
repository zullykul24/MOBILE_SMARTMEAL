package com.example.myapplication.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.fragments.FragmentSignIn;
import com.example.myapplication.fragments.FragmentSignUp;

public class LoginActivity extends AppCompatActivity {


    Button signInTextBtn, signUpTextBtn;
    ConstraintLayout signInLayout, signUpLayout;


    protected void AnhXa(){
        signInTextBtn = (Button) findViewById(R.id.signInButton);
        signUpTextBtn = (Button) findViewById(R.id.signUpButton);
        signInLayout = (ConstraintLayout) findViewById(R.id.signInLayout);
        signUpLayout = (ConstraintLayout) findViewById(R.id.signUpLayout);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();

        final FragmentManager fragmentManager = getSupportFragmentManager();

        final FragmentSignIn fragmentSignIn = new FragmentSignIn();
        final FragmentSignUp fragmentSignUp= new FragmentSignUp();


        fragmentManager.beginTransaction().add(R.id.frameSign, fragmentSignIn, "signin").addToBackStack("signin").commit();






        signUpTextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.frameSign, new FragmentSignUp()).addToBackStack(null).commit();
                signUpTextBtn.setTextColor(Color.parseColor("#FFFFFF"));
                signInTextBtn.setTextColor(Color.parseColor("#786464"));
            }
        });

        signInTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.frameSign,new FragmentSignIn()).addToBackStack(null).commit();

                signInTextBtn.setTextColor(Color.parseColor("#FFFFFF"));
                signUpTextBtn.setTextColor(Color.parseColor("#786464"));
            }
        });



    }
    /// prevent back to app after sign out
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}