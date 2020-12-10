package com.dakiiii.hungerwarrior;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.dakiiii.hungerwarrior.networking.CartRepo;
import com.dakiiii.hungerwarrior.networking.OrderRepo;

public class OrderViewModel extends AndroidViewModel {

    OrderRepo eOrderRepo;
    CartRepo eCartRepo;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        eOrderRepo = new OrderRepo(application);
        eCartRepo = new CartRepo(application);

    }

    public void sendOrderRequest(){
        eOrderRepo.sendOrderRequest();
    }

    public void emptyCart() {
        eCartRepo.deleteAll();
    }


}
