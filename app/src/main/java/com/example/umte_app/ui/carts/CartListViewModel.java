package com.example.umte_app.ui.carts;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.models.repos.CartRepository;

import java.util.List;

public class CartListViewModel extends AndroidViewModel {

    private CartRepository repository;
    private LiveData<List<Cart>> carts;

    public CartListViewModel(@NonNull Application application){
        super(application);
        repository = new CartRepository(application);
        carts = repository.getAllEditableCarts();
    }

    public void insert(Cart cart){
        repository.insert(cart);
    }
    public void update(Cart cart){
        repository.update(cart);
    }
    public void delete(Cart cart){
        repository.delete(cart);
    }
    public LiveData<List<Cart>> getCarts(){
        return carts;
    }



}
