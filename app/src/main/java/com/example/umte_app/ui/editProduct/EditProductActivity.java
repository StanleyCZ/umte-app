package com.example.umte_app.ui.editProduct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.umte_app.R;

public class EditProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText productName_editText;
    private EditText productCount_editText;
    private EditText productPrice_editText;
    private ImageView productImage_View;
    private Button openCameraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        productName_editText = findViewById(R.id.productNameEdit);
        productCount_editText = findViewById(R.id.productCountEdit);
        productPrice_editText = findViewById(R.id.productPriceEdit);
        openCameraButton = findViewById(R.id.buttonOpenCamera);


        setTitle("Nový produkt");





        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
    }



    private void saveProduct(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_entity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_entity:
                saveProduct(); return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //TODO NABINDOVAT POLOZKY
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}