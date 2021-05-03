package com.example.umte_app.ui.editProduct;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.umte_app.R;
import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.models.entities.CartItem;
import com.example.umte_app.ui.cartList.CartListActivity;

import java.io.File;

public class EditProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText productName_editText;
    private EditText productCount_editText;
    private EditText productPrice_editText;
    private ImageView productImage_View;
    private Button openCameraButton;
    private Bitmap productImage;

    private CartItem product;
    private EditProductViewModel viewModel;

    private static final int REQUEST_IMAGE_CAPTURE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        productName_editText = findViewById(R.id.productNameEdit);
        productCount_editText = findViewById(R.id.productCountEdit);
        productPrice_editText = findViewById(R.id.productPriceEdit);
        openCameraButton = findViewById(R.id.buttonOpenCamera);
        productImage_View = findViewById(R.id.productImagePreview);


        Intent intent = getIntent();
        long cartId = intent.getLongExtra("cart-id",0);
        if(intent.hasExtra("product-to-edit")){
            setTitle("Editace produktu");
            product = (CartItem) intent.getSerializableExtra("product-to-edit");
            product.cartId = cartId;

            productName_editText.setText(product.name);
            productCount_editText.setText(String.valueOf(product.count));
            productPrice_editText.setText(String.valueOf(product.price));
            if(product.imagePath != null && !product.imagePath.isEmpty()){
                try {
                    File imgFile = new File(product.imagePath);
                    if(imgFile.exists()){
                        productImage_View.setImageURI(Uri.fromFile(imgFile));
                    }
                }
                catch (Exception ex){
                    Toast.makeText(this,"Nepodařilo se načíst obrázek produktu",Toast.LENGTH_SHORT).show();
                }
            }

        }
        else{
            setTitle("Nový produkt");
            product = new CartItem();
            product.cartId = cartId;
            product.count = 1;
        }


        productName_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { product.name = s.toString(); }
        });
        productPrice_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                product.price = Float.parseFloat(s.toString());
            }
        });
        productCount_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                product.count = Integer.parseInt(s.toString().isEmpty() ? "1" : s.toString());
            }
        });

        viewModel = new EditProductViewModel(getApplication());


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
    }



    private void saveProduct(){

        if(product.name == null || product.name.trim().isEmpty()){
            Toast.makeText(this,"Vyplnte nazev kosiku",Toast.LENGTH_SHORT).show();
            return;
        }
        if(getIntent().hasExtra("product-to-edit")){
            viewModel.update(product, productImage);
        }
        else{
            viewModel.insert(product, productImage);
        }
        setResult(RESULT_OK);
        finish();


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

    //SENSOR 1 - CAMERA
    public void takePicture(View view) {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try{
                startActivityForResult(cameraIntent,REQUEST_IMAGE_CAPTURE);
            }
            catch (Exception ex){
                Toast.makeText(this,"Nepodařilo se otevřít fotoaparát",Toast.LENGTH_SHORT).show();
            }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");

            productImage = image;
            productImage_View.setImageBitmap(image);
        }
    }
}