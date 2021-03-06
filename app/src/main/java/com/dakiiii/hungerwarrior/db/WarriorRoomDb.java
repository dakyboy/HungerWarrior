package com.dakiiii.hungerwarrior.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dakiiii.hungerwarrior.db.dao.CartDao;
import com.dakiiii.hungerwarrior.db.dao.FoodDao;
import com.dakiiii.hungerwarrior.db.dao.OrderItemDao;
import com.dakiiii.hungerwarrior.model.Cart;
import com.dakiiii.hungerwarrior.model.Food;
import com.dakiiii.hungerwarrior.model.Order;
import com.dakiiii.hungerwarrior.model.OrderItem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Food.class, Cart.class, OrderItem.class, Order.class}, version = 1, exportSchema = false)
public abstract class WarriorRoomDb extends RoomDatabase {

    public abstract CartDao eCartDao();

    public abstract FoodDao eFoodDao();

    public abstract OrderItemDao eOrderItemDao();


    private static volatile WarriorRoomDb sWarriorRoomDb;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriterEXECUTOR_SERVICE =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static WarriorRoomDb getWarriorRoomDb(final Context context) {

        if (sWarriorRoomDb == null) {
            synchronized (WarriorRoomDb.class) {
                if (sWarriorRoomDb == null) {
                    sWarriorRoomDb = Room.databaseBuilder(context.getApplicationContext(),
                            WarriorRoomDb.class, "Warrior_database")
                            .build();
                }
            }
        }
        return sWarriorRoomDb;
    }

    public static void destroyDb() {
        sWarriorRoomDb = null;
    }

}
