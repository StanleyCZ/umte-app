package com.example.umte_app.ui.detailCart;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.umte_app.models.daos.CartItemDao;
import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.models.entities.CartItem;
import com.example.umte_app.models.entities.CartWithItems;
import com.example.umte_app.models.repos.CartRepository;
import com.example.umte_app.models.repos.ProductRepository;

import java.util.List;

public class DetailViewModel extends AndroidViewModel {

    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private LiveData<List<CartItem>> products;
    private Cart cart;


    public DetailViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
        cartRepository = new CartRepository(application);

    }

    public void setCart(Cart cart) {
        this.cart = cart;
        products = productRepository.getAllByCartId(cart.id);
    }
    public Cart getCart(){
        return cart;
    }
    public LiveData<List<CartItem>> getProducts(){
        return products;
    }
}
