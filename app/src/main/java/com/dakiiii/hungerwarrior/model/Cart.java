package com.dakiiii.hungerwarrior.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_table")
public class Cart {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Food eFood;

    private int eQuantity;

    public Cart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Food getFood() {
        return eFood;
    }

    public void setFood(Food food) {
        eFood = food;
    }

    public int getQuantity() {
        return eQuantity;
    }

    public void setQuantity(int quantity) {
        eQuantity = quantity;
    }
}
