package com.dakiiii.hungerwarrior;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dakiiii.hungerwarrior.adapter.CartAdapter;
import com.dakiiii.hungerwarrior.db.WarriorRoomDb;
import com.dakiiii.hungerwarrior.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView eCartRecyclerView;
    private CartAdapter eCartAdapter;
    private WarriorRoomDb eWarriorRoomDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        eCartRecyclerView = findViewById(R.id.recyclerViewCart);
        eCartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eCartAdapter = new CartAdapter();
        eCartRecyclerView.setAdapter(eCartAdapter);
        eWarriorRoomDb = WarriorRoomDb.getWarriorRoomDb(this);

        getCartItems();


    }

    private void getCartItems() {
        Thread thread = new Thread(() -> {
            List<Cart> cartList = new ArrayList<>();
            List<Cart> cartsDb = eWarriorRoomDb.eCartDao().getCarts();
            cartList.addAll(cartsDb);
            runOnUiThread(() -> {
                eCartAdapter.setCartItems(cartList);
            });
        });

        thread.start();
    }
}