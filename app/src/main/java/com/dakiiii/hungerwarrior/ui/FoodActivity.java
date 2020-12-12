package com.dakiiii.hungerwarrior.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dakiiii.hungerwarrior.R;
import com.dakiiii.hungerwarrior.adapter.AllFoodsAdapter;
import com.dakiiii.hungerwarrior.model.Cart;
import com.dakiiii.hungerwarrior.model.Food;
import com.dakiiii.hungerwarrior.ui.viewmodel.FoodActViewModel;

public class FoodActivity extends AppCompatActivity {

    TextView eFoodNameTextView;
    TextView eFoodPriceTextView;
    EditText eFoodQuantityEditText;
    Food eFood;
    private TextView eFoodDescTextView;

    FoodActViewModel eFoodActViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

//        Action bar
        Toolbar toolbar = findViewById(R.id.toolbar_Food);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Food");
        actionBar.setDisplayShowTitleEnabled(true);

        eFoodNameTextView = findViewById(R.id.textViewFoodName);
        eFoodPriceTextView = findViewById(R.id.textViewFoodPrice);
        eFoodQuantityEditText = findViewById(R.id.editTextQuantity);
        eFoodDescTextView = findViewById(R.id.textViewFoodDesc);
        eFood = new Food();


        eFoodActViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication()).create(FoodActViewModel.class);

//        get intent from user selection
        Intent intent = getIntent();
        int foodId = intent.getIntExtra(AllFoodsAdapter.EXTRA_FOOD_ID, 0);


        if (foodId != 0) {

            eFoodActViewModel.getFoodMutableLiveData(foodId).observe(this, new Observer<Food>() {
                @Override
                public void onChanged(Food food) {
                    eFood = food;
                    eFoodNameTextView.setText(food.getFoodName());
                    eFoodPriceTextView.setText(String.valueOf(food.getFoodPrice()));
                    eFoodDescTextView.setText(food.getFoodDescription());
                    eFoodQuantityEditText.setText("1");
                }
            });
        }


    }

    public void addToCart(View view) {
        int foodQty = Integer.parseInt(eFoodQuantityEditText.getText().toString().trim());

        saveOrderItem(eFood.getFoodId(), foodQty);


    }

    private void saveOrderItem(int foodId, int foodQty) {
        Cart cart = new Cart(foodId);
        cart.setQuantity(foodQty);
        eFoodActViewModel.insert(cart);
        Toast.makeText(FoodActivity.this, "Food added to cart", Toast.LENGTH_SHORT).show();

    }

    //    create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_food_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item_food_cart) {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}