package com.dakiiii.hungerwarrior.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders_table")
public class Order {

    @PrimaryKey
    private int orderId;


    private String customerId;


    private String orderedOn;

    public Order() {
    }

    @Ignore
    public Order(int orderId) {
        this.orderId = orderId;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderedOn() {
        return orderedOn;
    }

    public void setOrderedOn(String orderedOn) {
        this.orderedOn = orderedOn;
    }
}
