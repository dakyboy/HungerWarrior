package com.dakiiii.hungerwarrior.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_table")
public class Cart {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int foodId;

    @ColumnInfo(name = "quantity")
    private int quantity;

    public Cart(int foodId) {
        this.foodId = foodId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @NonNull
    @Override
    public String toString() {
        return this.toString();
    }
}
