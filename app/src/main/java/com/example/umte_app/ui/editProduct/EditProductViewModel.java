package com.example.umte_app.ui.editProduct;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.umte_app.models.entities.CartItem;
import com.example.umte_app.models.repos.ProductRepository;

public class EditProductViewModel extends AndroidViewModel {

    private ProductRepository productRepo;

    public EditProductViewModel(@NonNull Application application) {
        super(application);
        productRepo = new ProductRepository(application);
    }
    public void insert(CartItem item){

    }
    public void update(CartItem item){

    }
}
