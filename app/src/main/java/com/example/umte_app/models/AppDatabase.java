package com.example.umte_app.models;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.umte_app.models.daos.CartDao;
import com.example.umte_app.models.daos.CartItemDao;
import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.models.entities.CartItem;
import com.example.umte_app.utils.Converters;

@Database(version = 1, entities = {Cart.class, CartItem.class})
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "umte-db";
    private static AppDatabase instance;

    public abstract CartDao cartDao();
    public abstract CartItemDao itemsDao();

    public static synchronized AppDatabase getInstance(Context ctx){
        if(instance == null){
            instance = Room.databaseBuilder(ctx.getApplicationContext(),AppDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
        }
        return instance;
    }


}
