package com.dakiiii.hungerwarrior;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dakiiii.hungerwarrior.adapter.AllFoodsAdapter;
import com.dakiiii.hungerwarrior.db.WarriorRoomDb;
import com.dakiiii.hungerwarrior.model.Food;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView eRecyclerView;
    private AllFoodsAdapter eAllFoodsAdapter;
    private final List<Food> eFoodList = new ArrayList<>();
    private final String foodsUrl = "https://hungerwarrior.herokuapp.com/api/foods";
    private ConnectivityManager eConnectivityManager;
    private FoodViewModel eFoodViewModel;
    WarriorRoomDb eWarriorRoomDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        action bar
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);

        Intent intent = new Intent(this, CartActivity.class);
//        intialize floating action button to open cart activity
        FloatingActionButton floatingActionButtonCart = findViewById(R.id.floatingActionButton_cart);
        floatingActionButtonCart.setOnClickListener(v -> startActivity(intent));

        eRecyclerView = findViewById(R.id.recyclerView_AllFoods);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eAllFoodsAdapter = new AllFoodsAdapter(eFoodList);
        eRecyclerView.setAdapter(eAllFoodsAdapter);

        eFoodViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(FoodViewModel.class);

        eFoodViewModel.getFoodListLiveData().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                eAllFoodsAdapter.setFoods(foods);
            }
        });




    }
}