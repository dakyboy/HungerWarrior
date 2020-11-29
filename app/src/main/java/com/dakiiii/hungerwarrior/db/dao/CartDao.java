package com.dakiiii.hungerwarrior.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dakiiii.hungerwarrior.model.Cart;

import java.util.List;

@Dao
public interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Cart cart);

    @Query("DELETE FROM cart_table")
    void deleteAll();

    @Query("SELECT * FROM cart_table")
    List<Cart> getCarts();

    @Query("SELECT * FROM cart_table")
    LiveData<List<Cart>> getLiveCartItems();

    @Delete
    void deleteCartItem(Cart cart);

}
