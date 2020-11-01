package com.dakiiii.hungerwarrior;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.view.View;

import com.dakiiii.hungerwarrior.adapter.AllFoodsAdapter;
import com.dakiiii.hungerwarrior.login.FirebaseUiLoginActivity;
import com.dakiiii.hungerwarrior.model.Food;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    private RecyclerView eRecyclerView;
    private AllFoodsAdapter eAllFoodsAdapter;
    private List<Food> eFoodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eRecyclerView = findViewById(R.id.recyclerView_AllFoods);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addFoods();
        eAllFoodsAdapter = new AllFoodsAdapter(this, eFoodList);
        eRecyclerView.setAdapter(eAllFoodsAdapter);

    }
    
    public void addFoods(){
        Food food0 = new Food("Rolex", 1200);
        eFoodList.add(food0);
        Food food1 = new Food("Rolex", 3200);
        eFoodList.add(food1);
        Food food2 = new Food("Rolex", 2200);
        eFoodList.add(food2);
        Food food3 = new Food("Rolex", 1400);
        eFoodList.add(food3);
        Food food4 = new Food("Rolex", 1500);
        eFoodList.add(food4);
        Food food5 = new Food("Rolex", 1600);
        eFoodList.add(food5);
    }
}