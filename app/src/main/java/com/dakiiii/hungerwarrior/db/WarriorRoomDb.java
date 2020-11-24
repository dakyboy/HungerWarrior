package com.dakiiii.hungerwarrior.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.dakiiii.hungerwarrior.db.dao.CartDao;
import com.dakiiii.hungerwarrior.db.dao.FoodDao;
import com.dakiiii.hungerwarrior.model.Cart;
import com.dakiiii.hungerwarrior.model.Food;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Food.class, Cart.class}, version = 1, exportSchema = false)
public abstract class WarriorRoomDb extends RoomDatabase {

    public abstract CartDao eCartDao();
    public abstract FoodDao eFoodDao();

    private static volatile WarriorRoomDb sWarriorRoomDb;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriterEXECUTOR_SERVICE =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback sCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriterEXECUTOR_SERVICE.execute(()->{
                FoodDao foodDao = sWarriorRoomDb.eFoodDao();
                foodDao.deleteAll();
            });
        }
    };

    public static WarriorRoomDb getWarriorRoomDb(final Context context) {

        if (sWarriorRoomDb == null) {
            synchronized (WarriorRoomDb.class) {
                if (sWarriorRoomDb == null) {
                    sWarriorRoomDb = Room.databaseBuilder(context.getApplicationContext(),
                            WarriorRoomDb.class, "Warrior_database")
                            .addCallback(sCallback)
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
