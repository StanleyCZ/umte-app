package com.example.umte_app.models.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.umte_app.models.entities.CartItem;

import java.util.List;

@Dao
public interface CartItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(CartItem item);

    @Update
    void update(CartItem item);

    @Delete
    void delete(CartItem item);

    @Query("SELECT * from cartitem WHERE cartId = :idOfCart")
    LiveData<List<CartItem>> getAllByCartId(long idOfCart);

    @Query("SELECT count() from CartItem WHERE cartId = :idOfCart AND isPurchased=0")
    LiveData<Integer> getNotInsertedItems(long idOfCart);
}
