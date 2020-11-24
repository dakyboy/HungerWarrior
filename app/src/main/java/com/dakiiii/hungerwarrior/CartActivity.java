package com.dakiiii.hungerwarrior;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dakiiii.hungerwarrior.adapter.CartAdapter;

public class CartActivity extends AppCompatActivity {

    private RecyclerView eCartRecyclerView;
    private CartAdapter eCartAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        eCartRecyclerView = findViewById(R.id.recyclerViewCart);
        eCartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eCartAdapter = new CartAdapter();
        eCartRecyclerView.setAdapter(eCartAdapter);


    }
}