package com.dakiiii.hungerwarrior.networking;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.dakiiii.hungerwarrior.db.WarriorRoomDb;
import com.dakiiii.hungerwarrior.db.dao.FoodDao;
import com.dakiiii.hungerwarrior.model.Food;
import com.dakiiii.hungerwarrior.ui.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FoodRepo {

    private static final String FOODS_URL = "https://hungerwarrior.herokuapp.com/api/foods";


    private final LiveData<List<Food>> eFoodsData;
    MutableLiveData<Food> eFoodMutableLiveData = new MutableLiveData<>();
    private final FoodDao eFoodDao;
    public Context eContext;
    VolleySingleton eVolleySingleton;

    public FoodRepo(Application application) {
        WarriorRoomDb warriorRoomDb = WarriorRoomDb.getWarriorRoomDb(application);
        eFoodDao = warriorRoomDb.eFoodDao();
        eContext = application.getApplicationContext();

        eVolleySingleton = VolleySingleton.getInstance(application.getApplicationContext());
        getFoods(eVolleySingleton, eFoodDao);
        eFoodsData = eFoodDao.getAllFoodsLiveData();

//        new saveFoodsToDbAsyncTask().execute();
    }

    public void refreshFoodsDb() {
        getFoods(eVolleySingleton, eFoodDao);
    }

    //    get foods from hunger warrior api
    public static void getFoods(VolleySingleton volleySingleton, FoodDao foodDao) {


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
                        new saveFoodsToDbAsyncTask(foodDao).execute(food);
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
        volleySingleton.addToRequestQueue(jsonArrayRequest);
    }

    public LiveData<List<Food>> getFoodsData() {
        return eFoodsData;
    }


    public MutableLiveData<Food> getFoodById(int i) {
        new getFoodByIdAsyncTask(eFoodMutableLiveData, eFoodDao).execute(i);
        return eFoodMutableLiveData;
    }


    public static class getFoodByIdAsyncTask extends AsyncTask<Integer, Void, Food> {

        MutableLiveData<Food> eFoodMutableLiveData;
        FoodDao eFoodDao;

        public getFoodByIdAsyncTask(MutableLiveData<Food> foodMutableLiveData, FoodDao foodDao) {
            eFoodMutableLiveData = foodMutableLiveData;
            eFoodDao = foodDao;
        }

        @Override
        protected Food doInBackground(Integer... integers) {
            int foodId = integers[0];
            Food food = eFoodDao.getFood(foodId);
            return food;
        }

        @Override
        protected void onPostExecute(Food food) {
            super.onPostExecute(food);
            eFoodMutableLiveData.setValue(food);


        }
    }

    public static class saveFoodsToDbAsyncTask extends AsyncTask<Food, Void, Void> {
        FoodDao eFoodDao;

        public saveFoodsToDbAsyncTask(FoodDao foodDao) {
            eFoodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Food... foods) {
            eFoodDao.insertFood(foods[0]);
            return null;
        }

    }

}

