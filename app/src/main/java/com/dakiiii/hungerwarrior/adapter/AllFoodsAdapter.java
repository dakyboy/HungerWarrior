package com.dakiiii.hungerwarrior.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dakiiii.hungerwarrior.FoodActivity;
import com.dakiiii.hungerwarrior.R;
import com.dakiiii.hungerwarrior.model.Food;

import java.util.List;

public class AllFoodsAdapter extends RecyclerView.Adapter<AllFoodsAdapter.AllFoodsViewHolder> {

    public static final String EXTRA_FOOD_ID = "com.dakiiii.hungerwarrior.EXTRA.FOOD_ID";
    private List<Food> eFoods;
    private final Context eContext;

    public AllFoodsAdapter(Context context, List<Food> foods) {
        eFoods = foods;
        eContext = context;
    }


    @NonNull
    @Override
    public AllFoodsAdapter.AllFoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(eContext).inflate(R.layout.all_foods_row_item,
                parent, false);
        return new AllFoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllFoodsAdapter.AllFoodsViewHolder holder, int position) {
        Food food = eFoods.get(position);

        holder.eTextViewFoodName.setText(food.getFoodName());
//        holder.eTextViewFoodDescription.setText(food.getFoodDescription());
        holder.eTextViewFoodPrice.setText(Integer.toString(food.getFoodPrice()));

    }

    @Override
    public int getItemCount() {

        if (eFoods!= null) {
            return eFoods.size();
        }
        return 0;
    }

    public void setFoods(List<Food> foods) {
        eFoods = foods;
        notifyDataSetChanged();
    }

    public class AllFoodsViewHolder extends RecyclerView.ViewHolder {

        TextView eTextViewFoodName;
//        TextView eTextViewFoodDescription;
        TextView eTextViewFoodPrice;
        ImageView eImageViewFoodImage;

        public AllFoodsViewHolder(@NonNull View itemView) {
            super(itemView);

            eTextViewFoodName = itemView.findViewById(R.id.textView_FoodName);
            eTextViewFoodPrice = itemView.findViewById(R.id.textView_FoodPrice);
            eImageViewFoodImage = itemView.findViewById(R.id.imageViewFood);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context context = itemView.getContext();
                    Intent intent  = new Intent(context, FoodActivity.class);
                    Food food = eFoods.get(getAdapterPosition());

                    intent.putExtra(EXTRA_FOOD_ID, food.getFoodId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
