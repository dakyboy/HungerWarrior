package com.dakiiii.hungerwarrior.networking;

import android.app.Application;

import com.dakiiii.hungerwarrior.db.WarriorRoomDb;
import com.dakiiii.hungerwarrior.db.dao.CartDao;
import com.dakiiii.hungerwarrior.model.Cart;

import java.util.List;

public class CartRepo {
    private final CartDao eCartDao;

    private final List<Cart> eCarts;

    CartRepo(Application application) {
        WarriorRoomDb warriorRoomDb = WarriorRoomDb
                .getWarriorRoomDb(application);
        eCartDao = warriorRoomDb.eCartDao();
        eCarts = eCartDao.getCarts();

    }

    public List<Cart> getCarts() {
        return eCarts;
    }

    public void insert(Cart cart){
        WarriorRoomDb.databaseWriterEXECUTOR_SERVICE
                .execute(()-> {
                    eCartDao.insert(cart);
                });
    }
}
