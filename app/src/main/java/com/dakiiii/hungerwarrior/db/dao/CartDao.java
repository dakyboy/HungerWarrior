package com.dakiiii.hungerwarrior.db.dao;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dakiiii.hungerwarrior.model.Cart;
import com.dakiiii.hungerwarrior.model.Food;

import java.util.List;

public interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Cart cart);

    @Query("DELETE FROM cart_table")
    void deleteAll();

    @Query("SELECT * FROM cart_table")
    List<Food> getFoods();
}
