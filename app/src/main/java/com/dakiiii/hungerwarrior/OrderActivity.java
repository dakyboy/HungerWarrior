package com.dakiiii.hungerwarrior;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.dakiiii.hungerwarrior.model.Order;
import com.dakiiii.hungerwarrior.model.OrderRequest;

public class OrderActivity extends AppCompatActivity {

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

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frag_container_orders, new AddOrderDetailsFragment())
                .addToBackStack(null)
                .commit();

        eOrderViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(OrderViewModel.class);

    }

    public void sendOrder(View view) {
        eOrderViewModel.sendOrderRequest();
        Toast.makeText(this, "we temp made it", Toast.LENGTH_SHORT).show();
    }
}