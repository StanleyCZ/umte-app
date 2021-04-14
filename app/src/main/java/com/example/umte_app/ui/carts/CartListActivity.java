package com.example.umte_app.ui.carts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.umte_app.R;
import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.ui.history.HistoryActivity;
import com.example.umte_app.ui.maps.MapsActivity;
import com.example.umte_app.ui.newCart.NewCartActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CartListActivity extends AppCompatActivity {

    private static final int CREATE_CART_REQUEST = 1;
    private CartsViewModel cartsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        //název obrazovky
        setTitle("Seznam košíků");

        //Nastavení navigace
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_CartList);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_History:
                        startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_Maps:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_CartList:
                        return true;
                }
                return false;
            }
        });

        //viewmodel
        cartsViewModel = new ViewModelProvider(this).get(CartsViewModel.class);

        //button na vytvoreni noveho kosiku
        FloatingActionButton fab = findViewById(R.id.btn_addNewCart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartListActivity.this, NewCartActivity.class);
                startActivityForResult(intent, CREATE_CART_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.basketList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        CartAdapter adapter = new CartAdapter();
        recyclerView.setAdapter(adapter);

        cartsViewModel.getCarts().observe(this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                adapter.setCarts(carts);
            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CREATE_CART_REQUEST && requestCode == RESULT_OK){

            //TODO uložit data získaná z aktivity (nový košík)
            Toast.makeText(this,"Ulozeno",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Neuloženo",Toast.LENGTH_SHORT).show();
        }
    }

}