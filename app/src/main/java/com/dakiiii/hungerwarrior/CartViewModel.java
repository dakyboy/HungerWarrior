package com.dakiiii.hungerwarrior;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dakiiii.hungerwarrior.model.Cart;
import com.dakiiii.hungerwarrior.networking.CartRepo;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private final CartRepo eCartRepo;
    private final LiveData<List<Cart>> eListLiveData;

    private MutableLiveData<Integer> eCartTotal = new MutableLiveData<>();

    public CartViewModel(@NonNull Application application) {
        super(application);
        eCartRepo = new CartRepo(application);
        eListLiveData = eCartRepo.getLiveCartItems();
        eCartTotal = eCartRepo.getCartTotal();
    }

    public LiveData<List<Cart>> getListLiveData() {
        return eListLiveData;
    }


    public MutableLiveData<Integer> getCartTotal() {
        return eCartTotal;
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
