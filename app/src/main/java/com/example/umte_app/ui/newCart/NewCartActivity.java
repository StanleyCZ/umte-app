package com.example.umte_app.ui.newCart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.umte_app.R;
import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.ui.carts.CartListViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class NewCartActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText etTitle;
    private EditText etDate;
    private Spinner storeSpinner;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private Cart cart;

    private String storeName;
    private Date dateOfShopping;

    private NewCartViewModel viewModel;

    public static final String EXTRA_CART = "com.example.umte_app.EXTRA_CART";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cart);

        setTitle("Nový košík");
        viewModel = new ViewModelProvider(this).get(NewCartViewModel.class);

        etTitle = findViewById(R.id.edit_cart_Title);
        etDate = findViewById(R.id.edit_cart_shopDate);
        storeSpinner = findViewById(R.id.edit_cart_store);

        //nastaveni spinneru pro dropdown se seznamem obchodu
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.list_of_shops, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeSpinner.setAdapter(adapter);
        storeSpinner.setOnItemSelectedListener(this);

        //nastavení pro date picker
        etDate.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpDialog = new DatePickerDialog(NewCartActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
            dpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dpDialog.show();
        });
        //listener pro výběr datumu
        dateSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            Calendar cal = Calendar.getInstance();
            cal.set(year,month,dayOfMonth);
            dateOfShopping = cal.getTime();
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            //String formatedDate = dayOfMonth + "." + month + "." + year;
            etDate.setText(df.format(dateOfShopping));
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
        String title = etTitle.getText().toString();
        String storeName = storeSpinner.getSelectedItem().toString();

        if(title.trim().isEmpty()){
            Toast.makeText(this,"Vyplňte název košíku", Toast.LENGTH_SHORT).show();
            return;
        }

        cart = new Cart(title,storeName);
        cart.shopDate = dateOfShopping;

        viewModel.insert(cart);

        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        storeName = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}