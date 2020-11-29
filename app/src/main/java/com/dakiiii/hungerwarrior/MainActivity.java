package com.dakiiii.hungerwarrior;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.dakiiii.hungerwarrior.adapter.AllFoodsAdapter;
import com.dakiiii.hungerwarrior.db.WarriorRoomDb;
import com.dakiiii.hungerwarrior.model.Food;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

//        mvvm


        eConnectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        eWarriorRoomDb = WarriorRoomDb.getWarriorRoomDb(this);
//        List<Food> foods = WebService.

//        populateFoodList();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void populateFoodList() {
        NetworkInfo networkInfo = eConnectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        if (isConnected && eFoodList != null) {
            fetchFromServer();
        } else {
            fetchFromDb();
        }
    }

    private void fetchFromDb() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Food> foodList = eWarriorRoomDb
                        .eFoodDao()
                        .getAllFoods();

                eFoodList.clear();
                eFoodList.addAll(foodList);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        eAllFoodsAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        thread.start();
    }

    private void fetchFromServer() {
        getFoods();
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
                        String food_vendor = jsonObject.getString("food_vendor");

                        Food food = new Food(food_name, food_price);
                        food.setFoodId(foodId);
                        food.setFoodDescription(food_desc);
                        food.setFoodVendor(food_vendor);

                        eFoodList.add(food);
                        saveFoodToDb(food);
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
                fetchFromDb();
                Log.e(MainActivity.class.toString(), error.toString());
                progressDialog.dismiss();
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    private void saveFoodToDb(Food food) {
        WarriorRoomDb.databaseWriterEXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                eWarriorRoomDb.eFoodDao().insertFood(food);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WarriorRoomDb.destroyDb();
    }
}