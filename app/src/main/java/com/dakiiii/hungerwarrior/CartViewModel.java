package com.dakiiii.hungerwarrior;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dakiiii.hungerwarrior.model.Cart;
import com.dakiiii.hungerwarrior.networking.CartRepo;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private final CartRepo eCartRepo;
    private final LiveData<List<Cart>> eListLiveData;

//    Cart Activity
public CartViewModel(@NonNull Application application) {
    super(application);
    eCartRepo = new CartRepo(application);
    eListLiveData = eCartRepo.getLiveCartItems();
}

    public LiveData<List<Cart>> getListLiveData() {
        return eListLiveData;
    }

    public void insertCartItem(Cart cart) {
        eCartRepo.insert(cart);
    }

    public void deleteCartItem(Cart cart) {
        eCartRepo.deleteCart(cart);
    }

    public void deleteAllCartItems() {
        eCartRepo.deleteAll();
    }

}
