package com.example.umte_app.ui.editCart;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.models.repos.CartRepository;

public class EditCartViewModel extends AndroidViewModel {

    private CartRepository repository;

    public EditCartViewModel(@NonNull Application application) {
        super(application);
        repository = new CartRepository(application);
    }
    public void insert(Cart cart){
        long newCartId = repository.insert(cart);
    }
    public void update(Cart cart){repository.update(cart);}
}
