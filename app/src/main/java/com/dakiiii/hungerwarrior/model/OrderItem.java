package com.dakiiii.hungerwarrior.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "order_items_table")
public class OrderItem {
    @PrimaryKey
    private int id;
    private int orderId;
    private int foodId;
    private int quantity;
}
