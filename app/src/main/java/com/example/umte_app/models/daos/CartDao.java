package com.example.umte_app.models.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.umte_app.models.entities.Cart;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    long insert(Cart cart);

    @Update
    void update(Cart cart);

    @Delete
    void delete(Cart cart);

    @Query("SELECT * FROM cart WHERE isFinished = 0")
    LiveData<List<Cart>> getAllPrepared();

    @Query("SELECT * FROM cart WHERE isFinished = 1")
    LiveData<List<Cart>> getAllFinished();

    @Query("DELETE FROM cart WHERE isFinished = 1")
    void deleteCartsInHistory();

}
