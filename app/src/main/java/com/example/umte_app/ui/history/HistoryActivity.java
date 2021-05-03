package com.example.umte_app.ui.history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.umte_app.R;
import com.example.umte_app.ui.cartList.CartAdapter;
import com.example.umte_app.ui.cartList.CartListActivity;
import com.example.umte_app.ui.cartList.CartListViewModel;
import com.example.umte_app.ui.maps.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HistoryActivity extends AppCompatActivity implements SensorEventListener {

    private HistoryViewModel viewModel;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private boolean isSensorAvailable;
    private float currentX, lastX, diffX;
    private boolean notFirstTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_History);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_History:
                        return true;
                    case R.id.nav_Maps:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_CartList:
                        startActivity(new Intent(getApplicationContext(), CartListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        setTitle("Historie nákupů");


        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.historyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        HistoryItemAdapter adapter = new HistoryItemAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.getCarts().observe(this, carts -> adapter.setCarts(carts));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isSensorAvailable = true;

        }else{
            Toast.makeText(this, "Zařízení není dostupné", Toast.LENGTH_SHORT).show();
            isSensorAvailable = false;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float xShake = event.values[0];
        currentX = event.values[0];

        if(notFirstTime){
            diffX = Math.abs(lastX - currentX);
            notFirstTime = false;

            if(diffX > 5f){
                viewModel.deleteHistory();
                Toast.makeText(this,"Historie smazána", Toast.LENGTH_SHORT).show();
            }

        }
        lastX = currentX;
        notFirstTime = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isSensorAvailable){
            sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isSensorAvailable){
            sensorManager.unregisterListener(this);
        }
    }
}