package com.dakiiii.hungerwarrior.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int eOrderId;
    private static int eOrderTotal;
    private static List<Food> eFoods = new ArrayList<>();

    public Order(List<Food> foods) {
        eFoods = foods;
    }

    public int getOrderId() {
        return eOrderId;
    }

    public void setOrderId(int orderId) {
        eOrderId = orderId;
    }

    public int getOrderTotal() {
        return eOrderTotal;
    }

    public void setOrderTotal(int orderTotal) {
        eOrderTotal = orderTotal;
    }

    public List<Food> getFoods() {
        return eFoods;
    }

    public void setFoods(List<Food> foods) {
        eFoods = foods;
    }

    public static void addFoodToCart(Food food) {
        eFoods.add(food);
    }
}
