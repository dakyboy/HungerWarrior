package com.dakiiii.hungerwarrior;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dakiiii.hungerwarrior.adapter.AllFoodsAdapter;
import com.dakiiii.hungerwarrior.db.WarriorRoomDb;
import com.dakiiii.hungerwarrior.model.Cart;
import com.dakiiii.hungerwarrior.model.Food;
import com.dakiiii.hungerwarrior.model.Order;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    TextView eFoodNameTextView;
    TextView eFoodPriceTextView;
    WarriorRoomDb eWarriorRoomDb;
    EditText eFoodQuantityEditText;
    Food eFood;
    private TextView eFoodDescTextView;
    List<Food> eFoods;
    static Order eOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        eFoodNameTextView = findViewById(R.id.textViewFoodName);
        eFoodPriceTextView = findViewById(R.id.textViewFoodPrice);
        eFoodQuantityEditText = findViewById(R.id.editTextQuantity);
        eFoodDescTextView = findViewById(R.id.textViewFoodDesc);
        eFood = new Food();
        eWarriorRoomDb = WarriorRoomDb.getWarriorRoomDb(this);


        eFoods = new ArrayList<>();
//        eOrder = new Order(eFoods);
//        get intent from user selection
        Intent intent = getIntent();
        int foodId = intent.getIntExtra(AllFoodsAdapter.EXTRA_FOOD_ID, 0);

        if (foodId != 0) {
            WarriorRoomDb.databaseWriterEXECUTOR_SERVICE.execute(new Runnable() {
                @Override
                public void run() {
                    eFood = eWarriorRoomDb.eFoodDao().getFood(foodId);
                    eFoodNameTextView.setText(eFood.getFoodName());
                    eFoodPriceTextView.setText(Integer.toString(eFood.getFoodPrice()));
                    eFoodDescTextView.setText(eFood.getFoodDescription());
                    eFoodQuantityEditText.setText("1");

                }
            });

            /*eFoodNameTextView.setText(eFood.getFoodName());
            eFoodPriceTextView.setText(eFood.getFoodPrice());*/

        }


    }

    public void addToCart(View view) {
        int foodQty = Integer.parseInt(eFoodQuantityEditText.getText().toString().trim());

//        Order.addFoodToCart(food);
        saveOrderItem(eFood.getFoodId(), foodQty);
        Toast.makeText(this, "Food added to cart", Toast.LENGTH_SHORT).show();

    }

    private void saveOrderItem(int foodId, int foodQty) {
        WarriorRoomDb.databaseWriterEXECUTOR_SERVICE.execute(() -> {
            Cart cart = new Cart(foodId);
            cart.setQuantity(foodQty);
            eWarriorRoomDb.eCartDao().insert(cart);
        });
    }
}