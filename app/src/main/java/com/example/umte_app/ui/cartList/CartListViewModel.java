package com.example.umte_app.ui.cartList;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.models.entities.CartWithItems;
import com.example.umte_app.models.repos.CartRepository;

import java.util.List;

public class CartListViewModel extends AndroidViewModel {

    private CartRepository repository;
    private LiveData<List<CartWithItems>> carts;

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
    public LiveData<List<CartWithItems>> getCarts(){
        return carts;
    }



}
