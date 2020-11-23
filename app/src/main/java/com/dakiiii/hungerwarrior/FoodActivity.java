package com.dakiiii.hungerwarrior;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dakiiii.hungerwarrior.model.Food;
import com.dakiiii.hungerwarrior.model.Order;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    TextView eFoodNameTextView;
    TextView eFoodPriceTextView;
    EditText eFoodQuantityEditText;

    String food_name;
    int food_price;
    List<Food> eFoods;
    static Order  eOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        eOrder = new Order(eFoods);
        eFoods = new ArrayList<>();
        Intent intent =getIntent();
        Bundle bundle = intent.getBundleExtra("FOOD_DETAILS");
        eFoodNameTextView = findViewById(R.id.textViewFoodName);
        eFoodPriceTextView = findViewById(R.id.textViewFoodPrice);
        eFoodQuantityEditText = findViewById(R.id.editTextQuantity);

        food_price = bundle.getInt("FOOD_PRICE");
        eFoodPriceTextView.setText(food_price);

        food_name = bundle.getString("FOOD_NAME");
        eFoodNameTextView.setText(food_name);


        eFoodQuantityEditText.setText(Integer.toString(1));

    }

    public void addToCart(View view) {
        int food_quantity = Integer.parseInt(eFoodQuantityEditText.getText().toString());

        Food food = new Food(food_name, food_price);
        Order.addFoodToCart(food);
        Toast.makeText(this, "Food added to cart", Toast.LENGTH_SHORT).show();

    }
}