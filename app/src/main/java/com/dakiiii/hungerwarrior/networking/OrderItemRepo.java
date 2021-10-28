package com.dakiiii.hungerwarrior.networking;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dakiiii.hungerwarrior.db.WarriorRoomDb;
import com.dakiiii.hungerwarrior.db.dao.OrderItemDao;
import com.dakiiii.hungerwarrior.model.OrderItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class OrderItemRepo {

    public static final String ORDER_ITEM_STATUS_PENDING = "/pending";
    private final WarriorRoomDb eWarriorRoomDb;
    private final OrderItemDao eOrderItemDao;
    private final FirebaseUser eFirebaseUser;
    private final VolleySingleton eVolleySingleton;
    private LiveData<List<OrderItem>> eLiveDataPendingOrders;
    public static final String API_ORDER_ITEMS = "https://hungerwarrior.herokuapp.com/api/orderItemsByCustomer/";

    public OrderItemRepo(Application application) {
        eWarriorRoomDb = WarriorRoomDb.getWarriorRoomDb(application);
        eVolleySingleton = VolleySingleton.getInstance(application);
        eOrderItemDao = eWarriorRoomDb.eOrderItemDao();

        eFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }


    public void getOrderItems() {
        String url = API_ORDER_ITEMS + eFirebaseUser.getDisplayName();
        StringRequest stringRequest = new StringRequest(Request.Method.GET
                , url
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        int orderItemId = jsonObject.getInt("id");
                        int orderQty = jsonObject.getInt("quantity");
                        int orderId = jsonObject.getInt("order_id");
                        String foodName = jsonObject.getString("food_name");
                        String status = jsonObject.getString("status");

//                        Create order item
                        OrderItem orderItem = new OrderItem();
                        orderItem.setFoodName(foodName);
                        orderItem.setId(orderItemId);
                        orderItem.setOrderId(orderId);
                        orderItem.setQuantity(orderQty);
                        orderItem.setStatus(status);
//                        save order item to room db
                        WarriorRoomDb.databaseWriterEXECUTOR_SERVICE.execute(new Runnable() {
                            @Override
                            public void run() {
                                eOrderItemDao.insert(orderItem);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



        eVolleySingleton.addToRequestQueue(stringRequest);
    }

    public LiveData<List<OrderItem>> getLiveDataOrderItemsByStatus(String status){
        return eOrderItemDao.getPendingOrderItems(status);
    }


}
