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

    public LiveData<List<Cart>> getLiveCartItems() {
        return eCartDao.getLiveCartItems();
    }

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
}
