package com.example.umte_app.models.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CartWithItems {

    @Embedded public Cart cart;

    @Relation(parentColumn = "id",entityColumn = "cartId")
    public List<CartItem> items;

    public CartWithItems(Cart cart, List<CartItem> items){
        this.cart = cart;
        this.items = items;
    }

}
