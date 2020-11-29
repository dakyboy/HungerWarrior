package com.dakiiii.hungerwarrior.networking;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.dakiiii.hungerwarrior.db.WarriorRoomDb;
import com.dakiiii.hungerwarrior.db.dao.CartDao;
import com.dakiiii.hungerwarrior.model.Cart;

import java.util.List;

public class CartRepo {
    private final CartDao eCartDao;

    private final LiveData<List<Cart>> eCarts;

    public CartRepo(Application application) {
        WarriorRoomDb warriorRoomDb = WarriorRoomDb
                .getWarriorRoomDb(application);
        eCartDao = warriorRoomDb.eCartDao();
        eCarts = eCartDao.getLiveCartItems();

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

    /*private void getCartTotal(List<Cart> carts) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < carts.size(); i++) {
                int foodPrice = eWarriorRoomDb.eFoodDao()
                        .getFood(carts.get(i).getFoodId()).getFoodPrice();

                int itemQuantity = carts.get(i).getQuantity();
                int itemCost = itemQuantity * foodPrice;
                total += itemCost;
            }

            runOnUiThread(() -> {
                eCartTotalTextView.setText(Integer.toString(total));
            });
        });
        thread.start();
    }
    }*/

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
