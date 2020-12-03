package com.dakiiii.hungerwarrior;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dakiiii.hungerwarrior.adapter.CartAdapter;
import com.dakiiii.hungerwarrior.model.Cart;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView eCartRecyclerView;
    private CartAdapter eCartAdapter;
    TextView eCartTotalTextView;
    int total = 0;
    private CartViewModel eCartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

//        Action bar
        Toolbar toolbarCart = findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbarCart);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

//        recycler view
        eCartRecyclerView = findViewById(R.id.recyclerViewCart);
        eCartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eCartAdapter = new CartAdapter();
        eCartRecyclerView.setAdapter(eCartAdapter);
        eCartTotalTextView = findViewById(R.id.textViewTotalAmount);

//        View Models
        eCartViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(CartViewModel.class);

//        cart items list
        eCartViewModel.getListLiveData().observe(this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                eCartAdapter.setCartItems(carts);

            }
        });


//        cart items total

        eCartViewModel.getCartTotal().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                eCartTotalTextView.setText(String.valueOf(integer));
            }
        });

//        item click listener
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0
                        , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Cart cart = eCartAdapter.getCartAtPosition(position);
                        Toast.makeText(CartActivity.this
                                , "deleting cart item", Toast.LENGTH_SHORT).show();
                        eCartViewModel.deleteCartItem(cart);


                    }
                });

        itemTouchHelper.attachToRecyclerView(eCartRecyclerView);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_item_clearCart:
                Toast.makeText(this, "Clearing cart", Toast.LENGTH_SHORT).show();
                eCartViewModel.deleteAllCartItems();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void placeOrder(View view) {

        startActivity(new Intent(this, OrderActivity.class));
    }
}