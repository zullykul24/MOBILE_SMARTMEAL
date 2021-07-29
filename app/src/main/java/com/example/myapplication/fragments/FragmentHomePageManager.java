package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.activities.ATest;
import com.example.myapplication.activities.AddFood;
import com.example.myapplication.activities.DishCounting;
import com.example.myapplication.activities.History;
import com.example.myapplication.activities.KitchenDrink;
import com.example.myapplication.activities.KitchenFood;
import com.example.myapplication.activities.NewFood;
import com.example.myapplication.activities.Payment;
import com.example.myapplication.activities.WaiterFoodReady;
import com.example.myapplication.data_local.DataLocalManager;
import com.example.myapplication.models.Account;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentHomePageManager extends Fragment {
    ImageButton addFoodBtn, kitchenDrinkBtn, newFoodBtn, paymentBtn, historyBtn, menuFoodBtn;
    ImageButton kitchenFoodBtn;
    ImageButton readyFoodBtn;
    ImageButton dishCountingBtn;
    BottomNavigationView navbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView =  inflater.inflate(R.layout.homepage_fragment_for_manager, container, false);
        ///// ánh xạ
        addFoodBtn = rootView.findViewById(R.id.manager_add_food_btn);
        dishCountingBtn = rootView.findViewById(R.id.dish_counting_btn);


        newFoodBtn = rootView.findViewById(R.id.manage_new_food);
        paymentBtn = rootView.findViewById(R.id.manager_payment);
        historyBtn = rootView.findViewById(R.id.history_btn);


        // for waiter
        readyFoodBtn = rootView.findViewById(R.id.waiter_ready_food);
        //

        menuFoodBtn = rootView.findViewById(R.id.menuFood);
        kitchenFoodBtn = rootView.findViewById(R.id.kitchen_food_btn);
        kitchenDrinkBtn = rootView.findViewById(R.id.kitchen_drink_ordered);

        final FragmentManager fragmentManager = getFragmentManager();
        Account account = DataLocalManager.getLoggedinAccount();
        ////
        /// glide để bo góc ảnh
        Glide.with(this).load(R.drawable.new_food11).circleCrop().into(newFoodBtn);
        Glide.with(this).load(R.drawable.juice).circleCrop().into(kitchenDrinkBtn);
        Glide.with(this).load(R.drawable.history).circleCrop().into(historyBtn);
        Glide.with(this).load(R.drawable.food_menu).circleCrop().into(menuFoodBtn);
        Glide.with(this).load(R.drawable.payment).circleCrop().into(paymentBtn);
        Glide.with(this).load(R.drawable.ordered_food).circleCrop().into(kitchenFoodBtn);
        Glide.with(this).load(R.drawable.statistics).circleCrop().into(dishCountingBtn);


        // for waiter
        Glide.with(this).load(R.drawable.new_food).circleCrop().into(readyFoodBtn);
        //

        navbar = (BottomNavigationView) getActivity().findViewById(R.id.navbar);
        ////
        /// thêm món
        addFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFood = new Intent(getActivity(), AddFood.class);
                startActivity(intentFood);
            }
        });

        //các món bếp đã làm xong
        readyFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFood = new Intent(getActivity(), WaiterFoodReady.class);
                startActivity(intentFood);
            }
        });

        //thống kê
        dishCountingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DishCounting.class);
                startActivity(intent);
            }
        });

        /// thực đơn
        menuFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                        .replace(R.id.fragment_container,new FragmentMenuFood(), "MENU_FOOD")
                        .addToBackStack(null)

                        .commit();

                navbar.getMenu().findItem(R.id.nav_item_3).setChecked(true);
            }
        });
        //món mới
        newFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewFood.class);
                startActivity(intent);
            }
        });

        // món đã đặt, nhà bếp làm theo yêu cầu, dành cho role Kitchen staff
        kitchenFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KitchenFood.class);
                startActivity(intent);
            }
        });

        // đồ uống đã đặt, nhà bếp làm theo yêu cầu, dành cho role Kitchen staff
        kitchenDrinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KitchenDrink.class);
                startActivity(intent);
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), History.class);
                startActivity(intent);
            }
        });


        // thanh toán
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToPayment = new Intent(getActivity().getApplicationContext(), Payment.class);
                startActivity(intentToPayment);
            }
        });
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        return rootView;
    }
}
