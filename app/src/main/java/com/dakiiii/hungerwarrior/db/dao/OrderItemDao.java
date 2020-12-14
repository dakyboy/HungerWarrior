package com.dakiiii.hungerwarrior.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dakiiii.hungerwarrior.model.OrderItem;

import java.util.List;

@Dao
public interface OrderItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OrderItem orderItem);

    @Query("SELECT * FROM orderItems_table")
    LiveData<List<OrderItem>> getAllOrderItems();

    @Query("SELECT * FROM orderItems_table WHERE orderId = :orderId")
    LiveData<List<OrderItem>> getOrderItemByOrderId(int orderId);

}
