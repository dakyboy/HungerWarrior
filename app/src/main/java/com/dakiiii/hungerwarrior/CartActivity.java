package com.dakiiii.hungerwarrior;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dakiiii.hungerwarrior.adapter.CartAdapter;
import com.dakiiii.hungerwarrior.db.WarriorRoomDb;
import com.dakiiii.hungerwarrior.db.dao.CartDao;
import com.dakiiii.hungerwarrior.model.Cart;

import java.util.ArrayList;
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

            }
        });

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

    private void deleteCartItem(Cart cart) {
        new deleteCartItemAsyncTask(eCartDao).execute(cart);
        eCartAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        getCartItems();

    }

    private void getCartItems() {
        Thread thread = new Thread(() -> {
            List<Cart> cartList = new ArrayList<>();
            List<Cart> cartsDb = eWarriorRoomDb.eCartDao().getCarts();

            cartList.addAll(cartsDb);


            getCartTotal(cartList);

            runOnUiThread(() -> {
                eCartAdapter.setCartItems(cartList);

            });
        });


        thread.start();

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

    private static class deleteCartItemAsyncTask extends AsyncTask<Cart, Void, Void> {
        private final CartDao eCartDao;

        deleteCartItemAsyncTask(CartDao cartDao) {
            eCartDao = cartDao;

        }


        @Override
        protected Void doInBackground(Cart... carts) {
            eCartDao.deleteCartItem(carts[0]);
            return null;
        }

    }
}