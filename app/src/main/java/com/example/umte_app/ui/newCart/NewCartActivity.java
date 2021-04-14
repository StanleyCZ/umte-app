package com.example.umte_app.ui.newCart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.umte_app.R;

public class NewCartActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText etTitle;
    private EditText etDate;
    private Spinner storeSpinner;
    private String storeName;

    private static final String EXTRA_TITLE = "com.example.umte_app.EXTRA_TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cart);

        etTitle = findViewById(R.id.edit_cart_Title);
        etDate = findViewById(R.id.edit_cart_shopDate);
        storeSpinner = findViewById(R.id.edit_cart_store);

        //nastaveni pro dropdown se seznamem obchodu
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.list_of_shops, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeSpinner.setAdapter(adapter);
        storeSpinner.setOnItemSelectedListener(this);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
        setTitle("Nový košík");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_cart_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_cart:
                saveCart(); return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
    private void saveCart(){
        String title = etTitle.getText().toString();

        if(title.trim().isEmpty()){
            Toast.makeText(this,"Vyplňte název košíku", Toast.LENGTH_SHORT).show();
            return;
        }

        /*Intent data = new Intent();
        data.putExtra(Intent.EXTRA_TITLE, title);
        data.putExtra()*/

        Toast.makeText(this,"Uloženo", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        storeName = parent.getItemAtPosition(position).toString();
        Toast.makeText(this,storeName,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}