package com.dakiiii.hungerwarrior.networking;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dakiiii.hungerwarrior.db.WarriorRoomDb;
import com.dakiiii.hungerwarrior.db.dao.CartDao;
import com.dakiiii.hungerwarrior.db.dao.FoodDao;
import com.dakiiii.hungerwarrior.model.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepo {
    private static final String ORDERS_URL = "https://hungerwarrior.herokuapp.com/api/orders";
    public static final String KEY_ORDER_CUSTOMER_ID = "customer_id";
    public static final String KEY_ORDER_CART_ITEMS = "order_items";
    public static final String KEY_ORDER_ORDER_TOTAL = "order_total";


    VolleySingleton eVolleySingleton;
    CartDao eCartDao;
    FoodDao eFoodDao;
    FirebaseUser eFirebaseUser;


    public OrderRepo(Application application) {
        eVolleySingleton = VolleySingleton.getInstance(application.getApplicationContext());
        WarriorRoomDb warriorRoomDb = WarriorRoomDb.getWarriorRoomDb(application);
        eFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        eCartDao = warriorRoomDb.eCartDao();
        eFoodDao = warriorRoomDb.eFoodDao();

    }

    public void sendOrderRequest() {
        new sendOrderRequestAsyncTask(eFoodDao, eCartDao, eVolleySingleton, eFirebaseUser).execute();

    }


    private static class sendOrderRequestAsyncTask extends AsyncTask<Void, Void, Void> {
        CartDao eCartDao;
        FoodDao eFoodDao;
        FirebaseUser eFirebaseUser;
        private final VolleySingleton eVolleySingleton;

        public sendOrderRequestAsyncTask(FoodDao foodDao, CartDao cartDao
                , VolleySingleton volleySingleton, FirebaseUser firebaseUser) {

            eCartDao = cartDao;
            eFoodDao = foodDao;
            eFirebaseUser = firebaseUser;
            eVolleySingleton = volleySingleton;
        }

        @Override
        protected Void doInBackground(Void... voids) {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, ORDERS_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Fear not ye man", response);



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Fear this ye", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
//                orderRequest.se

                    List<Cart> cartList = eCartDao.getCarts();
                    int total = 0;
                    for (int i = 0; i < cartList.size(); i++) {
                        Cart cart = cartList.get(i);
                        int quantity = cart.getQuantity();
                        int foodPrice = eFoodDao.getFood(cart.getFoodId()).getFoodPrice();
                        int itemCost = foodPrice * quantity;
                        total += itemCost;
                    }

                    Gson gson = new Gson();

                    Map<String, String> params = new HashMap<>();
                    params.put(KEY_ORDER_CUSTOMER_ID, eFirebaseUser.getDisplayName());
                    params.put(KEY_ORDER_CART_ITEMS, gson.toJson(cartList));
                    params.put(KEY_ORDER_ORDER_TOTAL, String.valueOf(total));
                    Log.d("who dis", params.toString());
                    return params;
                }
            };

            stringRequest.setRetryPolicy(
                    new DefaultRetryPolicy(0
                            , DefaultRetryPolicy.DEFAULT_MAX_RETRIES
                            , DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            eVolleySingleton.addToRequestQueue(stringRequest);

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            WarriorRoomDb.databaseWriterEXECUTOR_SERVICE.execute(new Runnable() {
                @Override
                public void run() {
                    eCartDao.deleteAll();
                }
            });

        }
    }
}
