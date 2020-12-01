package com.dakiiii.hungerwarrior.networking;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dakiiii.hungerwarrior.db.WarriorRoomDb;
import com.dakiiii.hungerwarrior.db.dao.CartDao;
import com.dakiiii.hungerwarrior.db.dao.FoodDao;
import com.dakiiii.hungerwarrior.model.Cart;

import java.util.List;

public class CartRepo {
    private static final String TAG = "CartRepo";
    private final CartDao eCartDao;
    FoodDao eFoodDao;

    private final LiveData<List<Cart>> eCarts;
    MutableLiveData<Integer> eCartTotal = new MutableLiveData<>();

    public CartRepo(Application application) {
        WarriorRoomDb warriorRoomDb = WarriorRoomDb
                .getWarriorRoomDb(application);


        eCartDao = warriorRoomDb.eCartDao();
        eFoodDao = warriorRoomDb.eFoodDao();

        eCarts = eCartDao.getLiveCartItems();


    }

    public void insert(Cart cart) {
        WarriorRoomDb.databaseWriterEXECUTOR_SERVICE
                .execute(() -> {
                    eCartDao.insert(cart);
                });
    }

    public MutableLiveData<Integer> getCartTotal() {
        new calculateCartTotalAsyncTask(eFoodDao, eCartDao, eCartTotal).execute();
        return eCartTotal;
    }

    public void deleteCart(Cart cartItem) {
        new deleteCartItemAsyncTask(eCartDao, eFoodDao, eCartTotal).execute(cartItem);
    }

    public void deleteAll() {
        new deleteAllItemCostAsyncTask(eCartDao, eCartTotal).execute();
    }

    public LiveData<List<Cart>> getLiveCartItems() {
        return eCartDao.getLiveCartItems();
    }


//        AsyncTask Inner classes

    private static class calculateCartTotalAsyncTask extends AsyncTask<List<Void>, Void, Integer> {

        FoodDao eFoodDao;
        CartDao eCartDao;
        MutableLiveData<Integer> eData;

        public calculateCartTotalAsyncTask(FoodDao foodDao, CartDao cartDao, MutableLiveData<Integer> liveData) {
            eFoodDao = foodDao;
            eCartDao = cartDao;
            eData = liveData;
        }


        @Override
        protected Integer doInBackground(List<Void>... lists) {

            return calculateCartTotal();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (eData == null) return;

            eData.setValue(integer);

        }

        private int calculateCartTotal() {
            int total = 0;
            List<Cart> cartList = eCartDao.getCarts();
            for (int i = 0; i < cartList.size(); i++) {
                Cart cart = cartList.get(i);
                int quantity = cart.getQuantity();
                int foodPrice = eFoodDao.getFood(cart.getFoodId()).getFoodPrice();
                int itemCost = foodPrice * quantity;
                total += itemCost;
            }
            return total;
        }

    }

    //    delete an from item in the cart
    private static class deleteCartItemAsyncTask extends AsyncTask<Cart, Void, Void> {
        private final CartDao eCartDao;
        FoodDao eFoodDao;
        MutableLiveData<Integer> eLiveData;

        deleteCartItemAsyncTask(CartDao cartDao, FoodDao foodDao, MutableLiveData<Integer> data) {
            eCartDao = cartDao;
            eFoodDao = foodDao;
            eLiveData = data;

        }


        @Override
        protected Void doInBackground(Cart... carts) {
            eCartDao.deleteCartItem(carts[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new calculateCartTotalAsyncTask(eFoodDao, eCartDao, eLiveData).execute();
        }
    }

    //Delete All items in cart
    private static class deleteAllItemCostAsyncTask extends AsyncTask<Void, Void, Void> {
        private final CartDao eCartDao;
        MutableLiveData<Integer> eLiveData;

        public deleteAllItemCostAsyncTask(CartDao cartDao, MutableLiveData data) {
            eCartDao = cartDao;
            eLiveData = data;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            eCartDao.deleteAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            eLiveData.setValue(0);
        }
    }

    private static class getCartItems extends AsyncTask<Void, Void, Void> {

        CartDao eCartDao;

        @Override
        protected Void doInBackground(Void... voids) {
            eCartDao.getCarts();
            return null;
        }
    }

}
