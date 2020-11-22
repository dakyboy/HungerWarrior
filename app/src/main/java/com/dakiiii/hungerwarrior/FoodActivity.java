package com.dakiiii.hungerwarrior;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FoodActivity extends AppCompatActivity {

    TextView eFoodNameTextView;
    TextView eFoodpriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Intent intent =getIntent();
        Bundle bundle = intent.getBundleExtra("FOOD_DETAILS");
        eFoodNameTextView = findViewById(R.id.textViewFoodName);
        eFoodpriceTextView = findViewById(R.id.textViewFoodPrice);

        eFoodpriceTextView.setText(Integer.toString(bundle.getInt("FOOD_PRICE")));
        eFoodNameTextView.setText(bundle.getString("FOOD_NAME"));

    }
}