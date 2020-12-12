package com.dakiiii.hungerwarrior.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.dakiiii.hungerwarrior.R;
import com.dakiiii.hungerwarrior.ui.viewmodel.OrderViewModel;

public class OrderActivity extends AppCompatActivity {

    private Button eButtonPlaceOrder;
    OrderViewModel eOrderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

//        get action bar
        Toolbar toolbar = findViewById(R.id.toolbar_orders);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        eButtonPlaceOrder = findViewById(R.id.button_SendOrder);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frag_container_orders, new OrderTrackActivity.AddOrderDetailsFragment())
                .addToBackStack(null)
                .commit();

        eOrderViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(OrderViewModel.class);

    }

    public void sendOrder(View view) {
        eButtonPlaceOrder.setEnabled(false);
        eOrderViewModel.sendOrderRequest();
        Toast.makeText(this, "Order sent", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}