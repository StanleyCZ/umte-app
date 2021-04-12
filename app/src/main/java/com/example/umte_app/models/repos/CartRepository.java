package com.example.umte_app.models.repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.umte_app.models.AppDatabase;
import com.example.umte_app.models.daos.CartDao;
import com.example.umte_app.models.entities.Cart;

import java.util.List;

public class CartRepository {

    private CartDao cartDao;
    private LiveData<List<Cart>> editableCarts;

    public CartRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        cartDao = db.cartDao();
        editableCarts = cartDao.getAllPrepared();
    }

    public void insert(Cart cart){new InsertCartAsyncTask(cartDao).execute(cart);}
    public void update(Cart cart){new UpdateCartAsyncTask(cartDao).execute(cart);}
    public void delete(Cart cart){new DeleteCartAsyncTask(cartDao).execute(cart);}
    public LiveData<List<Cart>> getAllEditableCarts(){
        return editableCarts;
    }
    public void deleteCartsHistory(){new DeleteCartAsyncTask(cartDao).execute();}

    private static class InsertCartAsyncTask extends AsyncTask<Cart,Void,Void>{
        private CartDao cartDao;

        public InsertCartAsyncTask(CartDao cartDao){this.cartDao = cartDao;}
        @Override
        protected Void doInBackground(Cart... carts) {
            cartDao.insert(carts[0]);
            return null;
        }
    }
    private static class UpdateCartAsyncTask extends AsyncTask<Cart,Void,Void>{
        private CartDao cartDao;

        public UpdateCartAsyncTask(CartDao cartDao){this.cartDao = cartDao;}
        @Override
        protected Void doInBackground(Cart... carts) {
            cartDao.update(carts[0]);
            return null;
        }
    }
    private static class DeleteCartAsyncTask extends AsyncTask<Cart,Void,Void>{
        private CartDao cartDao;

        public DeleteCartAsyncTask(CartDao cartDao){this.cartDao = cartDao;}
        @Override
        protected Void doInBackground(Cart... carts) {
            cartDao.delete(carts[0]);
            return null;
        }
    }
    private static class DeleteCartsHistoryAsyncTask extends AsyncTask<Void,Void,Void>{
        private CartDao cartDao;

        public DeleteCartsHistoryAsyncTask(CartDao dao){this.cartDao = dao;}
        @Override
        protected Void doInBackground(Void... voids) {
            cartDao.deleteCartsInHistory();
            return null;
        }
    }

}