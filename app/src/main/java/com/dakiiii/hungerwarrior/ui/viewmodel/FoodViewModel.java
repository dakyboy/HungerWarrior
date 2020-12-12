package com.dakiiii.hungerwarrior.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dakiiii.hungerwarrior.model.Food;
import com.dakiiii.hungerwarrior.networking.FoodRepo;

import java.util.List;

public class FoodViewModel extends AndroidViewModel {
    private final LiveData<List<Food>> eFoodListLiveData;
    FoodRepo eFoodRepo;

    public FoodViewModel(@NonNull Application application) {
        super(application);
        eFoodRepo = new FoodRepo(application);
        eFoodListLiveData = eFoodRepo.getFoodsData();


    }

    public LiveData<List<Food>> getFoodListLiveData() {
        return eFoodListLiveData;
    }

    public void getFoodsFromServer() {
        eFoodRepo.refreshFoodsDb();
    }
}
