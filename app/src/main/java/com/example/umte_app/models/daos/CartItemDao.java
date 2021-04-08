package com.example.umte_app.models.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;


import com.example.umte_app.models.entities.CartItem;

@Dao
public interface CartItemDao {

    @Insert
    long insert(CartItem item);

    @Update
    void update(CartItem item);

    @Delete
    void delete(CartItem item);
}
