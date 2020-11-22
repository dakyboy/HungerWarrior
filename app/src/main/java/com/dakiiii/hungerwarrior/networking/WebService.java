package com.dakiiii.hungerwarrior.networking;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.dakiiii.hungerwarrior.MainActivity;
import com.dakiiii.hungerwarrior.VolleySingleton;
import com.dakiiii.hungerwarrior.model.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WebService {
    private static String FOODS_URL = "https://hungerwarrior.herokuapp.com/api/foods";
    Context eContext;

    public WebService(Context context) {
        eContext = context;
    }

    public static List<Food> getFoods(Context context) {
        List<Food> foods = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET
                , FOODS_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
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
                        foods.add(food);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(MainActivity.class.toString(), error.toString());
            }
        });
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
        return foods;
    }
}
