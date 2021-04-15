package com.example.umte_app.ui.editCart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.umte_app.models.entities.Cart;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditCartActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText etTitle;
    private EditText etDate;
    private Spinner storeSpinner;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private Cart cart;


    private EditCartViewModel viewModel;
    private DateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cart);

        viewModel = new ViewModelProvider(this).get(EditCartViewModel.class);
        dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        //komponenty
        etTitle = findViewById(R.id.edit_cart_Title);
        etDate = findViewById(R.id.edit_cart_shopDate);
        storeSpinner = findViewById(R.id.edit_cart_store);

        //nastaveni spinneru pro dropdown se seznamem obchodu
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.list_of_shops, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeSpinner.setAdapter(adapter);
        storeSpinner.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        if(intent.hasExtra("cart-to-edit")){
            setTitle("Editace košíku");

            cart = (Cart) intent.getSerializableExtra("cart-to-edit");
            etTitle.setText(cart.name);
            etDate.setText(cart.shopDate != null ? dateFormat.format(cart.shopDate) : "");
            storeSpinner.setSelection(adapter.getPosition(cart.storeName));
        }
        else {
            setTitle("Nový košík");
            cart = new Cart();
        }

        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                cart.name = s.toString();
            }
        });


        //nastavení pro date picker
        etDate.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpDialog = new DatePickerDialog(EditCartActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
            dpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dpDialog.show();
        });
        //listener pro výběr datumu
        dateSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            Calendar cal = Calendar.getInstance();
            cal.set(year,month,dayOfMonth);
            cart.shopDate = cal.getTime();
            etDate.setText(dateFormat.format(cart.shopDate));
        };


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

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

        if(cart.name.trim().isEmpty()){
            Toast.makeText(this,"Vyplňte název košíku", Toast.LENGTH_SHORT).show();
            return;
        }

        if(getIntent().hasExtra("cart-to-edit")){
            viewModel.update(cart);
        }
        else{
            viewModel.insert(cart);
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cart.storeName = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}