package com.example.umte_app.ui.shopping;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.models.entities.CartItem;
import com.example.umte_app.models.entities.CartWithItems;
import com.example.umte_app.models.repos.CartRepository;
import com.example.umte_app.models.repos.ProductRepository;

import java.util.List;

public class ShoppingViewModel extends AndroidViewModel {

    private CartRepository cartRepo;
    private ProductRepository productRepo;
    private LiveData<List<CartItem>> shopItems;
    private Cart cart;


    public ShoppingViewModel(@NonNull Application application) {
        super(application);
        cartRepo = new CartRepository(application);
        productRepo = new ProductRepository(application);
    }

    public void setCart(Cart cart){
        this.cart = cart;
        shopItems = productRepo.getAllByCartId(this.cart.id);
    }
    public Cart getCart(){
        return cart;
    }
    public LiveData<List<CartItem>> getShopItems(){
        return shopItems;
    }
    public LiveData<Integer> getRemainingShopItems(long cartId){
        return productRepo.getRemainingShopItems(cartId);
    }
    public void update(CartItem item){
        productRepo.update(item);
    }
    public void updateCart(Cart cart){
        cartRepo.update(cart);
    }
}
