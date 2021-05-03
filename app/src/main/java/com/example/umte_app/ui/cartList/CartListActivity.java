package com.example.umte_app.ui.cartList;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umte_app.R;
import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.ui.detailCart.DetailActivity;
import com.example.umte_app.ui.editCart.EditCartActivity;
import com.example.umte_app.ui.history.HistoryActivity;
import com.example.umte_app.ui.maps.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CartListActivity extends AppCompatActivity {

    private static final int CREATE_CART_REQUEST = 1;
    public static final int EDIT_CART_REQUEST = 2;
    private static final int DETAIL_CART_REQUEST = 3;
    public static final int SHOPPING_REQUEST = 4;
    private CartListViewModel cartListViewModel;


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
        cartListViewModel = new ViewModelProvider(this).get(CartListViewModel.class);

        //button na vytvoreni noveho kosiku
        FloatingActionButton fab = findViewById(R.id.btn_addNewCart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartListActivity.this, EditCartActivity.class);
                startActivityForResult(intent, CREATE_CART_REQUEST);
            }
        });

        //recycler view se seznamem košíků
        RecyclerView recyclerView = findViewById(R.id.basketList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        CartAdapter adapter = new CartAdapter();
        recyclerView.setAdapter(adapter);

        cartListViewModel.getCarts().observe(this, carts -> adapter.setCarts(carts));

        //nastaveni swipování položek
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                cartListViewModel.delete(adapter.getCartAt(viewHolder.getAdapterPosition()).cart);
                Toast.makeText(CartListActivity.this,"Košík byl smazán", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        //nastaveni udalosti kliknuti na kosik
        adapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Cart cart) {
                Intent intent = new Intent(CartListActivity.this, DetailActivity.class);
                intent.putExtra("cart-detail",cart);
                startActivityForResult(intent, DETAIL_CART_REQUEST);
            }
        });

        //nastaveni udalosti po dlouhem kliknuti na kosik
        adapter.setOnItemLongClickListener(new CartAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Cart cart) {
                openDialog(cart);
            }
        });

    }

    //dialog pro vyber zda chce upravit kosik ci nakupovat
    public void openDialog(Cart cart){
        CartClikDialog dialog = new CartClikDialog(cart);
        dialog.show(getSupportFragmentManager(),"cart dialog");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case CREATE_CART_REQUEST:
                    Toast.makeText(this,"Košík byl uložen",Toast.LENGTH_SHORT).show(); break;
                case EDIT_CART_REQUEST:
                    Toast.makeText(this,"Košík byl upraven",Toast.LENGTH_SHORT).show(); break;
                case SHOPPING_REQUEST:
                    Toast.makeText(this,"Nákup byl donočen", Toast.LENGTH_SHORT).show();break;
            }
        }
        else{
            Toast.makeText(this,"Nebyly provedeny žádné změny",Toast.LENGTH_SHORT).show();
        }
    }

}