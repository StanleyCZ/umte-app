package com.example.umte_app.ui.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.umte_app.R;
import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.models.entities.CartItem;
import com.example.umte_app.ui.cartList.CartListActivity;
import com.example.umte_app.ui.detailCart.ProductAdapter;

import java.util.Calendar;

public class ShoppingActivity extends AppCompatActivity {

    private ShoppingViewModel viewModel;
    private Cart cart;

    private TextView remaining_TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        remaining_TextView = findViewById(R.id.shopping_leftToBuy);

        viewModel = new ViewModelProvider(this).get(ShoppingViewModel.class);
        Intent intent = getIntent();
        boolean cartExists = intent.hasExtra("shopping-cart");
        if(cartExists){
            cart = (Cart)intent.getSerializableExtra("shopping-cart");
            viewModel.setCart(cart);
        }
        RecyclerView recyclerView = findViewById(R.id.shoppingContent);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ShopItemAdapter adapter = new ShopItemAdapter();
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new ShopItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CartItem item) {
                item.isPurchased = !item.isPurchased;
                viewModel.update(item);
            }
        });
        viewModel.getShopItems().observe(this,shopitems -> {
            adapter.setCart(shopitems);
        });
        viewModel.getRemainingShopItems(viewModel.getCart().id).observe(this, count -> {
            remaining_TextView.setText(String.valueOf(count));
        });


        setTitle("Nákup");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.shopping_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.finish_shopping:
                finishShopping(); return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
    public void finishShopping(){
        cart.isFinished = true;
        cart.shopDate = Calendar.getInstance().getTime();
        viewModel.updateCart(cart);
        setResult(RESULT_OK);
        finish();
    }
}