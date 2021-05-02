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

    public LiveData<List<CartItem>> getAllByCartId(long cartId){
        return itemDao.getAllByCartId(cartId);
    }
    public long insert(CartItem product){
        new InsertProductAsyncTask(itemDao).execute(product);
        return product.id;
    }
    public void update(CartItem product){
        new UpdateProductAsyncTask(itemDao).execute(product);
    }


    private static class InsertProductAsyncTask extends AsyncTask<CartItem, Void,Long>{
        private CartItemDao dao;

        public InsertProductAsyncTask(CartItemDao dao){
            this.dao = dao;
        }

        @Override
        protected Long doInBackground(CartItem... cartItems) {
            return dao.insert(cartItems[0]);
        }
    }
    private static class UpdateProductAsyncTask extends AsyncTask<CartItem, Void,Void>{
        private CartItemDao dao;

        public UpdateProductAsyncTask(CartItemDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CartItem... cartItems) {
            dao.update(cartItems[0]);
            return null;
        }
    }
}
