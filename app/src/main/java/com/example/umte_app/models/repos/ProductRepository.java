package com.example.umte_app.models.repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.umte_app.models.AppDatabase;
import com.example.umte_app.models.daos.CartItemDao;
import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.models.entities.CartItem;
import com.example.umte_app.models.entities.CartWithItems;

import java.util.List;

public class ProductRepository {

    private CartItemDao itemDao;

    public ProductRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        itemDao = db.itemsDao();

    }

    public LiveData<List<CartItem>> getAllByCartId(int cartId){
        return itemDao.getAllByCartId(cartId);
    }


}
