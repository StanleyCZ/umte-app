package com.example.umte_app.models.repos;

import android.app.Application;
import android.os.AsyncTask;
import android.renderscript.RenderScript;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.umte_app.models.AppDatabase;
import com.example.umte_app.models.daos.CartDao;
import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.models.entities.CartWithItems;

import java.util.List;

public class CartRepository {

    private CartDao cartDao;
    private LiveData<List<CartWithItems>> editableCarts;
    private LiveData<List<CartWithItems>> historyCarts;
    private LiveData<CartWithItems> cart;

    public CartRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        cartDao = db.cartDao();
        editableCarts = cartDao.getAllReadyToShop();
        historyCarts = cartDao.getAllFinished();
    }

    public LiveData<List<CartWithItems>> getHistoryCarts(){
        return historyCarts;
    }

    public LiveData<CartWithItems> getById(int basketId){
        GetCartByIdAsyncTask task = new GetCartByIdAsyncTask(cartDao);
        task.execute(basketId);
        return task.getThisFukingCart();
    }
    public long insert(Cart cart){
        new InsertCartAsyncTask(cartDao).execute(cart);
        return cart.id;
    }
    public void update(Cart cart){new UpdateCartAsyncTask(cartDao).execute(cart);}
    public void delete(Cart cart){new DeleteCartAsyncTask(cartDao).execute(cart);}
    public LiveData<List<CartWithItems>> getAllEditableCarts(){
        return editableCarts;
    }
    public void deleteCartsHistory(){
        new DeleteCartsHistoryAsyncTask(cartDao).execute();
    }

    private static class InsertCartAsyncTask extends AsyncTask<Cart,Void, Long>{
        private CartDao cartDao;

        public InsertCartAsyncTask(CartDao cartDao){this.cartDao = cartDao;}
        @Override
        protected Long doInBackground(Cart... carts) {
            return cartDao.insert(carts[0]);
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

        @Override
        protected void onPostExecute(Void aVoid) {
            String wtf = "wtf";
            Log.println(Log.INFO,"blabla",wtf);
        }
    }

    private static class GetCartByIdAsyncTask extends AsyncTask<Integer,Void,LiveData<CartWithItems>>{
        private CartDao cartDao;
        private LiveData<CartWithItems> cwitems;

        public GetCartByIdAsyncTask(CartDao dao){
            this.cartDao = dao;
        }
        public LiveData<CartWithItems> getThisFukingCart(){
            return cwitems;
        };

        @Override
        protected LiveData<CartWithItems> doInBackground(Integer... integers) {
            return cartDao.getById(integers[0]);
        }

        @Override
        protected void onPostExecute(LiveData<CartWithItems> cartWithItemsLiveData) {
            super.onPostExecute(cartWithItemsLiveData);
            cwitems = cartWithItemsLiveData;
        }
    }

}
