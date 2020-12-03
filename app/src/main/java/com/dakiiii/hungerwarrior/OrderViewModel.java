package com.dakiiii.hungerwarrior;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.dakiiii.hungerwarrior.model.Order;
import com.dakiiii.hungerwarrior.model.OrderRequest;
import com.dakiiii.hungerwarrior.networking.OrderRepo;

public class OrderViewModel extends AndroidViewModel {

    OrderRepo eOrderRepo;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        eOrderRepo = new OrderRepo(application);

    }

    public void sendOrderRequest(){
        eOrderRepo.sendOrderRequest();
    }
}
