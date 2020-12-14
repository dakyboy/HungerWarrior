package com.dakiiii.hungerwarrior.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dakiiii.hungerwarrior.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrderTrackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_track);
        Toolbar toolbar = findViewById(R.id.toolbar_orders_track);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.pending_orders);
        actionBar.setDisplayShowTitleEnabled(true);

        BottomNavigationView bottomNavView = findViewById(R.id.bottomNavView);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag_container_orders, new NewOrdersFragment())
                .commit();

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.order_item_pending:
                        actionBar.setTitle(R.string.pending_orders);
                        loadFrag(new NewOrdersFragment());
                        return true;
                    case R.id.order_item_preparing:
                        actionBar.setTitle(R.string.preparing_orders);
                        loadFrag(new BeingPreparedOrdersFragment());
                        return true;
                    case R.id.order_item_cancelled:
                        actionBar.setTitle(R.string.cancelled_orders);
                        loadFrag(new CancelledOrdersFragment());
                        return true;
                    case R.id.order_item_completed:
                        actionBar.setTitle(R.string.completed_orders);
                        loadFrag(new CompletedOrdersFragment());
                        return true;
                }
                return false;
            }
        });


    }

    private void loadFrag(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_container_orders, fragment)
                .addToBackStack(null)
                .commit();
    }
}