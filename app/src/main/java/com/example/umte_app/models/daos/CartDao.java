package com.example.umte_app.models.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.models.entities.CartWithItems;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    long insert(Cart cart);

    @Update
    void update(Cart cart);

    @Delete
    void delete(Cart cart);

    @Transaction
    @Query("SELECT * FROM cart WHERE isFinished = 0")
    LiveData<List<CartWithItems>> getAllReadyToShop();

    @Query("SELECT * FROM cart WHERE isFinished = 1")
    LiveData<List<CartWithItems>> getAllFinished();

    @Query("DELETE FROM cart WHERE isFinished = 1")
    void deleteCartsInHistory();

    @Query("SELECT * FROM cart WHERE id = :basketId")
    LiveData<CartWithItems> getById(int basketId);
}
