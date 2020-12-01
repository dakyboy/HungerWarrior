package com.dakiiii.hungerwarrior;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.dakiiii.hungerwarrior.db.WarriorRoomDb;
import com.dakiiii.hungerwarrior.db.dao.CartDao;
import com.dakiiii.hungerwarrior.model.Cart;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView eCartRecyclerView;
    private CartAdapter eCartAdapter;
    private WarriorRoomDb eWarriorRoomDb;
    TextView eCartTotalTextView;
    int total = 0;
    private CartDao eCartDao;
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
        eWarriorRoomDb = WarriorRoomDb.getWarriorRoomDb(this);
        eCartDao = eWarriorRoomDb.eCartDao();
        eCartTotalTextView = findViewById(R.id.textViewTotalAmount);
        eCartViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(CartViewModel.class);

        eCartViewModel.getListLiveData().observe(this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                eCartAdapter.setCartItems(carts);
//                getCartTotal(carts);

            }
        });


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
            case R.id.clearCart:
                Toast.makeText(this, "Clearing cart", Toast.LENGTH_SHORT).show();
                eCartViewModel.deleteAllCartItems();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCartTotal(List<Cart> carts) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < carts.size(); i++) {
                int foodPrice = eWarriorRoomDb.eFoodDao()
                        .getFood(carts.get(i).getFoodId()).getFoodPrice();

                int itemQuantity = carts.get(i).getQuantity();
                int itemCost = itemQuantity * foodPrice;
                total += itemCost;
            }

            runOnUiThread(() -> {
                eCartTotalTextView.setText(Integer.toString(total));
            });
        });
        thread.start();
    }
}