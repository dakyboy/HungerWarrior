package com.dakiiii.hungerwarrior;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.dakiiii.hungerwarrior.adapter.AllFoodsAdapter;
import com.dakiiii.hungerwarrior.model.Food;
import com.dakiiii.hungerwarrior.networking.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView eRecyclerView;
    private AllFoodsAdapter eAllFoodsAdapter;
    private List<Food> eFoodList = new ArrayList<>();
    private String foodsUrl = "https://hungerwarrior.herokuapp.com/api/foods";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eRecyclerView = findViewById(R.id.recyclerView_AllFoods);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addFoods();
        eAllFoodsAdapter = new AllFoodsAdapter(this, eFoodList);
        eRecyclerView.setAdapter(eAllFoodsAdapter);

//        List<Food> foods = WebService.
        getFoods();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void addFoods() {
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

    public void getFoods() {
        eFoodList.clear();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET
                , foodsUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int foodId = jsonObject.getInt("id");
                        int food_price = jsonObject.getInt("food_price");
                        String food_name = jsonObject.getString("food_name");
                        String food_desc = jsonObject.getString("food_desc");

                        Food food = new Food(food_name, food_price);
                        food.setFoodId(foodId);
                        food.setFoodDescription(food_desc);

                        eFoodList.add(food);
                    }
                    eAllFoodsAdapter.setFoods(eFoodList);
                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                Log.e(MainActivity.class.toString(), error.toString());
                progressDialog.dismiss();
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }
}