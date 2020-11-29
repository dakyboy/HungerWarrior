package com.dakiiii.hungerwarrior.networking;

import android.app.Application;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.dakiiii.hungerwarrior.MainActivity;
import com.dakiiii.hungerwarrior.VolleySingleton;
import com.dakiiii.hungerwarrior.db.WarriorRoomDb;
import com.dakiiii.hungerwarrior.db.dao.CartDao;
import com.dakiiii.hungerwarrior.db.dao.FoodDao;
import com.dakiiii.hungerwarrior.model.Cart;
import com.dakiiii.hungerwarrior.model.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CartRepo {
    private final CartDao eCartDao;
    private FoodDao eFoodDao;



    private final LiveData<List<Cart>> eCarts;
    private LiveData<List<Food>> eFoods;
    private MutableLiveData<List<Food>> eMutableLiveDataFoods = new MutableLiveData<>();
    private List<Food> eFoodsFromWeb;

    VolleySingleton eVolleySingleton;
    public CartRepo(Application application) {
        WarriorRoomDb warriorRoomDb = WarriorRoomDb
                .getWarriorRoomDb(application);

        eFoodsFromWeb = WebService.getFoods(application);
        Log.d("Poka face", String.valueOf(eFoodsFromWeb.size()));
        eCartDao = warriorRoomDb.eCartDao();
        eCarts = eCartDao.getLiveCartItems();

        eFoodDao =warriorRoomDb.eFoodDao();
        eFoods = eFoodDao.getAllFoodsLiveData();

    }


    public void insert(Cart cart) {
        WarriorRoomDb.databaseWriterEXECUTOR_SERVICE
                .execute(() -> {
                    eCartDao.insert(cart);
                });
    }

    public void deleteCart(Cart cartItem) {
        new deleteCartItemAsyncTask(eCartDao).execute(cartItem);
    }

    public void deleteAll() {
        new deleteAllItemCostAsyncTask(eCartDao).execute();
    }

    public LiveData<List<Cart>> getLiveCartItems() {
        return eCartDao.getLiveCartItems();
    }

//    public int getCartTotal() {
//
//
//        return 0;
//    }


    public LiveData<List<Food>> getFoodsLiveData() {
        return eFoods;
    }

    public List<Food> getFoodsFromWeb() {
        return eFoodsFromWeb;
    }

    public void insertFood(Food food) {

    }

    private static class getCartTotal extends AsyncTask<List<Cart>, Void, Void> {

        @Override
        protected Void doInBackground(List<Cart>... lists) {


            return null;
        }
    }

    //    delete an from item in the cart
    private static class deleteCartItemAsyncTask extends AsyncTask<Cart, Void, Void> {
        private final CartDao eCartDao;

        deleteCartItemAsyncTask(CartDao cartDao) {
            eCartDao = cartDao;


        }


        @Override
        protected Void doInBackground(Cart... carts) {
            eCartDao.deleteCartItem(carts[0]);
            return null;
        }

    }

    //Delete All items in cart
    private static class deleteAllItemCostAsyncTask extends AsyncTask<Void, Void, Void> {
        private final CartDao eCartDao;

        public deleteAllItemCostAsyncTask(CartDao cartDao) {
            eCartDao = cartDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            eCartDao.deleteAll();
            return null;
        }
    }


}
