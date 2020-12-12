package com.dakiiii.hungerwarrior.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dakiiii.hungerwarrior.model.Cart;
import com.dakiiii.hungerwarrior.model.Food;
import com.dakiiii.hungerwarrior.networking.CartRepo;
import com.dakiiii.hungerwarrior.networking.FoodRepo;

public class FoodActViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> eLiveData = new MutableLiveData<>();
    private final MutableLiveData<Food> eFoodMutableLiveData = new MutableLiveData<>();
    FoodRepo eFoodRepo;
    CartRepo eCartRepo;


    public FoodActViewModel(@NonNull Application application) {
        super(application);
        eFoodRepo = new FoodRepo(application);
        eCartRepo = new CartRepo(application);
    }

    public void insert(Cart cart) {
        eCartRepo.insert(cart);
    }

    public MutableLiveData<Food> getFoodMutableLiveData(int foodId) {
        return eFoodRepo.getFoodById(foodId);
    }
}
