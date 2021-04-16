package com.example.umte_app.ui.detailCart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.umte_app.R;
import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.ui.cartList.CartAdapter;

public class DetailActivity extends AppCompatActivity {

    private DetailViewModel viewModel;

    private TextView totalCount_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Obsah košíku");

        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        Intent intent = getIntent();
        if(intent.hasExtra("cart-detail")){
            viewModel.setCart((Cart) intent.getSerializableExtra("cart-detail"));
        }

        totalCount_textView = findViewById(R.id.basketDetail_TotalCount);


        //recycler view se seznamem produktů v košíku
        RecyclerView recyclerView = findViewById(R.id.basketContent);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ProductAdapter adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.getProducts().observe(this, products -> {
            adapter.setProducts(products);
            totalCount_textView.setText(String.valueOf(products.size()));
        });


    }

}