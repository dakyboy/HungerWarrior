package com.dakiiii.hungerwarrior.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dakiiii.hungerwarrior.R;
import com.dakiiii.hungerwarrior.adapter.AllFoodsAdapter;
import com.dakiiii.hungerwarrior.model.Food;
import com.dakiiii.hungerwarrior.ui.viewmodel.FoodViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView eRecyclerView;
    private AllFoodsAdapter eAllFoodsAdapter;
    private final List<Food> eFoodList = new ArrayList<>();
    private FoodViewModel eFoodViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        action bar
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);

//        Recycler view for foods
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

        eFoodViewModel.getFoodsFromServer();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_item_cart:
                Intent intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}