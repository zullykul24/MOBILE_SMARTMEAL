package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapplication.R;

public class DishCounting extends AppCompatActivity {
    Button countDishByDishName, countDishByUsername;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_counting);
        countDishByDishName = findViewById(R.id.count_by_dishname);
        countDishByUsername = findViewById(R.id.count_by_username);
        backBtn = findViewById(R.id.dish_counting_backtohome_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        countDishByDishName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DishCounting.this, CountByDishName.class);
                startActivity(intent);
            }
        });
        countDishByUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DishCounting.this, CountByUserName.class);
                startActivity(intent);
            }
        });
    }
}