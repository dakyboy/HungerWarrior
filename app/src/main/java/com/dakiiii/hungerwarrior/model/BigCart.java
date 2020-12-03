package com.dakiiii.hungerwarrior.model;

import android.os.AsyncTask;

import com.dakiiii.hungerwarrior.db.WarriorRoomDb;
import com.dakiiii.hungerwarrior.db.dao.FoodDao;

import java.util.ArrayList;
import java.util.List;

public class BigCart {
    private List<Cart> eCarts = new ArrayList<>();
    private int cartTotal =0;

    public BigCart(List<Cart> carts) {
        eCarts = carts;
    }


    /*private void getCartTotal(List<Cart> carts) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < carts.size(); i++) {
                int foodPrice = eWarriorRoomDb.eFoodDao()
                        .getFood(carts.get(i).getFoodId()).getFoodPrice();

                int itemQuantity = carts.get(i).getQuantity();
                int itemCost = itemQuantity * foodPrice;
                total += itemCost;
            }

            runOnUiThread(() -> {
                eCartTotalTextView.setText(Integer.toString(total));
            });
        });
        thread.start();
    }
    }*/
    public void getCartTotal(FoodDao foodDao) {
        for (int i = 0; i < eCarts.size(); i++) {
            int itemQuantity = eCarts.get(i).getQuantity();
            Food food = foodDao.getFood(eCarts.get(i).getFoodId());
            int foodPrice = food.getFoodPrice();
            int itemCost = itemQuantity * foodPrice;
            cartTotal += itemCost;
        }
    }

    private static class getFoodPrice extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
