package com.dakiiii.hungerwarrior.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dakiiii.hungerwarrior.model.OrderItem;
import com.dakiiii.hungerwarrior.networking.OrderItemRepo;

import java.util.List;

public class OrderItemViewModel extends AndroidViewModel {
    private final OrderItemRepo eOrderItemRepo;
    private LiveData<List<OrderItem>> eLiveDataOrderItems;
    private LiveData<List<OrderItem>> eLiveDataPendingOrderItems;
    public OrderItemViewModel(@NonNull Application application) {
        super(application);
        eOrderItemRepo = new OrderItemRepo(application);
    }

    public LiveData<List<OrderItem>> getLiveDataOrderItems() {
        return eLiveDataOrderItems;
    }

    public LiveData<List<OrderItem>> getLiveDataPendingOrderItems() {
        return eOrderItemRepo.getLiveDataOrderItemsByStatus("pending");
    }

    public void getOrderItems() {
        eOrderItemRepo.getOrderItems();
    }


    public LiveData<List<OrderItem>> getLiveDataCompletedOrderItems() {
        return eOrderItemRepo.getLiveDataOrderItemsByStatus("completed");
    }
}
