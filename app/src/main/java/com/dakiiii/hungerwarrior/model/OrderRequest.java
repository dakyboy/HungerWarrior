package com.dakiiii.hungerwarrior.model;

import java.util.List;

public class OrderRequest {

    private List<Cart> eCarts;
    private int customerId;
    private int orderTotal;
    private String orderType;

    public OrderRequest(List<Cart> carts, int customerId, int orderTotal, String orderType) {
        eCarts = carts;
        this.customerId = customerId;
        this.orderTotal = orderTotal;
        this.orderType = orderType;
    }

    public OrderRequest() {

    }

    public List<Cart> getCarts() {
        return eCarts;
    }

    public void setCarts(List<Cart> carts) {
        eCarts = carts;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getOrderTotal() {
        for (int i = 0; i < eCarts.size(); i++) {

        }
        return orderTotal;
    }

    public void setOrderTotal(int orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
