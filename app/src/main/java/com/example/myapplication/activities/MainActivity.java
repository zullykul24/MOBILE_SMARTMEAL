package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.data_local.DataLocalManager;
import com.example.myapplication.fragments.FragmentHomePageManager;
import com.example.myapplication.fragments.FragmentHomePageKitchenStaff;
import com.example.myapplication.fragments.FragmentHomePageWaiter;
import com.example.myapplication.fragments.FragmentMenuFood;
import com.example.myapplication.fragments.FragmentTableOrder;
import com.example.myapplication.models.Account;
import com.example.myapplication.R;
import com.example.myapplication.fragments.FragmentAccount;
import com.example.myapplication.fragments.FragmentHomePageCashier;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navbar;
    private long backPressedTime;
    protected void AnhXa(){
        navbar = (BottomNavigationView) findViewById(R.id.navbar);

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();



        Account account = DataLocalManager.getLoggedinAccount();
        final FragmentAccount fragmentAccount = new FragmentAccount();
        final FragmentMenuFood fragmentMenuFood = new FragmentMenuFood();
        final FragmentTableOrder fragmentTableOrder = new FragmentTableOrder();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentHomePageCashier cashierFragmentHomePage = new FragmentHomePageCashier();
        final FragmentHomePageManager managerFragmentHomePage = new FragmentHomePageManager();
        final FragmentHomePageKitchenStaff kitchenStaffFragmentHomePage = new FragmentHomePageKitchenStaff();
        final FragmentHomePageWaiter waiterFragmentHomePage = new FragmentHomePageWaiter();

        switch (account.getRoleId()){
            case 1:
                fragmentManager.beginTransaction().add(R.id.fragment_container, managerFragmentHomePage, "MANAGER").commit();
                break;
            case 2:
                fragmentManager.beginTransaction().add(R.id.fragment_container, cashierFragmentHomePage, "CASHIER").commit();
                break;
            case 3:
                fragmentManager.beginTransaction().add(R.id.fragment_container, kitchenStaffFragmentHomePage, "KITCHEN_STAFF").commit();
                break;
            case 4:
                fragmentManager.beginTransaction().add(R.id.fragment_container, waiterFragmentHomePage, "WAITER").commit();
                break;
        }

        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_item_1:
                        switch (account.getRoleId()){
                            case 1:
                                fragmentManager.beginTransaction()
                                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                                        .replace(R.id.fragment_container, managerFragmentHomePage, "MANAGER")
                                        .addToBackStack(null)

                                        .commit();
                                break;
                            case 2:
                                fragmentManager.beginTransaction()
                                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                                        .replace(R.id.fragment_container, cashierFragmentHomePage, "CASHIER")
                                        .addToBackStack(null)

                                        .commit();
                                break;
                            case 3:
                                fragmentManager.beginTransaction()
                                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                                        .replace(R.id.fragment_container, kitchenStaffFragmentHomePage, "KITCHEN_STAFF")
                                        .addToBackStack(null)

                                        .commit();
                                break;
                            case 4:
                                fragmentManager.beginTransaction()
                                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                                        .replace(R.id.fragment_container, waiterFragmentHomePage, "WAITER")
                                        .addToBackStack(null)

                                        .commit();
                                break;
                        }

                        break;
                    case R.id.nav_item_2:
                        fragmentManager.beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                                .replace(R.id.fragment_container, fragmentTableOrder, "ORDER_TABLE")
                                .addToBackStack(null)

                                .commit();
                        break;
                    case R.id.nav_item_3:
                        fragmentManager.beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                                .replace(R.id.fragment_container, fragmentMenuFood, "MENU_FOOD")
                                .addToBackStack(null)

                                .commit();
                        break;
                    case R.id.nav_item_4:
                        fragmentManager.beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                                .replace(R.id.fragment_container, fragmentAccount, "ACCOUNT")
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

}