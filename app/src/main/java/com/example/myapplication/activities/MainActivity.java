package com.example.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Account;
import com.example.myapplication.R;
import com.example.myapplication.fragments.FragmentAccount;
import com.example.myapplication.fragments.FragmentHomePageCashier;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navbar;
    private long backPressedTime;
    protected void AnhXa(){
        navbar = (BottomNavigationView) findViewById(R.id.cashier_navbar);

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();

        //
        Intent intent = getIntent();
        Account account = (Account) intent.getSerializableExtra("Account_obj");
        Bundle bundle = new Bundle();
        bundle.putSerializable("Account_obj", account);
        final FragmentAccount fragmentAccount = new FragmentAccount();
        fragmentAccount.setArguments(bundle);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentHomePageCashier cashierFragmentHomePage = new FragmentHomePageCashier();



        fragmentManager.beginTransaction().add(R.id.cashier_fragment_container, cashierFragmentHomePage, "1").commit();
        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_item_1:
                        fragmentManager.beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                                .replace(R.id.cashier_fragment_container, cashierFragmentHomePage, "1")
                                .addToBackStack(null)

                                .commit();
                        break;
                    case R.id.nav_item_2:
                        fragmentManager.beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                                .replace(R.id.cashier_fragment_container, fragmentAccount, "2")
                                .addToBackStack(null)

                                .commit();
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            moveTaskToBack(true);
            return;
        } else {
            Toast.makeText(MainActivity.this, "Chạm lần nữa để thoát", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    public static void setHomeItem(Activity activity) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                activity.findViewById(R.id.cashier_navbar);
        bottomNavigationView.setSelectedItemId(R.id.nav_item_1);
    }
}