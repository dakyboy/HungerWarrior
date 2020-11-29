package com.dakiiii.hungerwarrior;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dakiiii.hungerwarrior.model.Food;
import com.dakiiii.hungerwarrior.networking.CartRepo;

import java.util.List;

public class FoodViewModel extends AndroidViewModel {
    CartRepo eCartRepo;
    private LiveData<List<Food>> eFoodListLiveData;
    public FoodViewModel(@NonNull Application application) {
        super(application);
        eCartRepo = new CartRepo(application);
        eFoodListLiveData = eCartRepo.getFoodsLiveData();

    }

    public LiveData<List<Food>> getFoodListLiveData() {
        return eFoodListLiveData;
    }
}
