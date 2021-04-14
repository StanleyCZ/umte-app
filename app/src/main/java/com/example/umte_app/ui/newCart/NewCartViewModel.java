package com.example.umte_app.ui.newCart;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.models.repos.CartRepository;

public class NewCartViewModel extends AndroidViewModel {

    private CartRepository repository;

    public NewCartViewModel(@NonNull Application application) {
        super(application);
        repository = new CartRepository(application);
    }
    public void insert(Cart cart){
        repository.insert(cart);
    }
}
