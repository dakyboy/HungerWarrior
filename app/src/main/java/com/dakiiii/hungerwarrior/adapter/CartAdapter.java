package com.dakiiii.hungerwarrior.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dakiiii.hungerwarrior.R;
import com.dakiiii.hungerwarrior.db.WarriorRoomDb;
import com.dakiiii.hungerwarrior.model.Cart;
import com.dakiiii.hungerwarrior.model.Food;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    public List<Cart> getCartItems() {
        return eCartItems;
    }

    private List<Cart> eCartItems = new ArrayList<>();
    private WarriorRoomDb eWarriorRoomDb;

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,
                parent, false);
        eWarriorRoomDb = WarriorRoomDb.getWarriorRoomDb(parent.getContext());
        return new CartViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = eCartItems.get(position);


        int quantity = cart.getQuantity();
        Thread thread = new Thread(() -> {
//            get food from db
            Food food = eWarriorRoomDb.eFoodDao().getFood(cart.getFoodId());
            holder.eCartFoodName.setText(quantity + " x " + food.getFoodName());
//             calculate cart total cost


            int foodPrice = food.getFoodPrice();
            int cost = quantity * foodPrice;
            String costText = Integer.toString(cost);
            holder.eCartFoodCost.setText(quantity + " x " + foodPrice + " = " + costText);

        });
        thread.start();


    }

    @Override
    public int getItemCount() {

        if (eCartItems != null) {
            return eCartItems.size();
        }
        return 0;
    }

    public void setCartItems(List<Cart> cartItems) {
        eCartItems = cartItems;
        notifyDataSetChanged();
    }


    public Cart getCartAtPosition(int position) {


        return eCartItems.get(position);

    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView eCartFoodName;
        TextView eCartFoodCost;
        TextView eCartTotalTextView;
        ImageButton eCartDeleteButton;
        ImageButton eCartEditButton;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            eCartFoodName = itemView.findViewById(R.id.textView_Cart_FoodName);
            eCartFoodCost = itemView.findViewById(R.id.textView_Cart_FoodCost);
            eCartDeleteButton = itemView.findViewById(R.id.imageButton_Cart_delete);
            eCartTotalTextView = itemView.findViewById(R.id.textViewTotalAmount);


        }

        private void deleteWord(Cart cart) {
        }


    }
}
