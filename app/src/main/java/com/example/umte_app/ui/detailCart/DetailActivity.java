package com.example.umte_app.ui.detailCart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.umte_app.R;
import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.models.entities.CartItem;
import com.example.umte_app.ui.cartList.CartAdapter;
import com.example.umte_app.ui.cartList.CartListActivity;
import com.example.umte_app.ui.editCart.EditCartActivity;
import com.example.umte_app.ui.editProduct.EditProductActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivity extends AppCompatActivity {

    private static final int CREATE_PRODUCT_REQUEST = 1;
    private static final int EDIT_PRODUCT_REQUEST = 2;
    private static final int DELETE_PRODUCT_REQUEST = 3;

    private DetailViewModel viewModel;

    private TextView totalCount_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        totalCount_textView = findViewById(R.id.basketDetail_TotalCount);

        //nastaveni viewmodelu
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        Intent intent = getIntent();
        if(intent.hasExtra("cart-detail")){
            viewModel.setCart((Cart) intent.getSerializableExtra("cart-detail"));
        }

        //FAB do přidání nového produktu
        FloatingActionButton fab = findViewById(R.id.btn_addProduct);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, EditProductActivity.class);
                intent.putExtra("cart-id", viewModel.getCart().id);
                startActivityForResult(intent, CREATE_PRODUCT_REQUEST);
            }
        });

        //recycler view se seznamem produktů v košíku
        RecyclerView recyclerView = findViewById(R.id.basketContent);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ProductAdapter adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);

        //bindování viewmodelu
        viewModel.getProducts().observe(this, products -> {
            adapter.setProducts(products);
            totalCount_textView.setText(String.valueOf(products.size()));
        });

        //TODO SWIPE POLOŽEK PRO MAZÁNÍ

        //Nastavení přechodu do editace produktu
        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CartItem product) {
                Intent intent = new Intent(DetailActivity.this,EditProductActivity.class);
                intent.putExtra("product-to-edit",product);
                intent.putExtra("cart-id", viewModel.getCart().id);
                startActivityForResult(intent, EDIT_PRODUCT_REQUEST);

            }
        });


        setTitle("Obsah košíku");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case CREATE_PRODUCT_REQUEST:
                    Toast.makeText(this, "Produkt byl vytvořen", Toast.LENGTH_SHORT).show(); break;
                case EDIT_PRODUCT_REQUEST:
                    Toast.makeText(this, "Produkt byl upraven", Toast.LENGTH_SHORT).show(); break;
            }
        }
        else{
            Toast.makeText(this,"Nebyly provedeny žádné změny",Toast.LENGTH_SHORT).show();
        }

    }
}