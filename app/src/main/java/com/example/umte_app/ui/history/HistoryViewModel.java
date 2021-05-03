package com.example.umte_app.ui.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.umte_app.models.entities.CartWithItems;
import com.example.umte_app.models.repos.CartRepository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {

    private CartRepository repository;
    private LiveData<List<CartWithItems>> carts;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        repository = new CartRepository(application);
        carts = repository.getHistoryCarts();
    }

    public LiveData<List<CartWithItems>> getCarts(){
        return carts;
    }

    public void deleteHistory(){
        repository.deleteCartsHistory();
    }
}
