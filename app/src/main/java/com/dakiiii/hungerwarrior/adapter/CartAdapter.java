package com.dakiiii.hungerwarrior.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dakiiii.hungerwarrior.R;
import com.dakiiii.hungerwarrior.model.Food;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private final List<Food> eFoods = new ArrayList<>();

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
//        Food food

    }

    @Override
    public int getItemCount() {

        if (eFoods != null) {
            return eFoods.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView eCartFoodName;
        TextView eCartFoodCost;
        Button eCartDeleteButton;
        Button eCartEditButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            eCartFoodName = itemView.findViewById(R.id.textView_Cart_FoodName);
            eCartFoodCost = itemView.findViewById(R.id.textView_Cart_FoodCost);
            eCartDeleteButton = itemView.findViewById(R.id.button_Cart_Delete);
            eCartEditButton = itemView.findViewById(R.id.button_Cart_Edit);
        }
    }
}
