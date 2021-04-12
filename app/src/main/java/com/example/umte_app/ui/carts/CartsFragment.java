package com.example.umte_app.ui.carts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.umte_app.R;
import com.example.umte_app.models.AppDatabase;
import com.example.umte_app.models.entities.Cart;

import java.util.List;

public class CartsFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //prirardi view layout
        View root = inflater.inflate(R.layout.fragment_carts, container,false);

        final TextView tv = (TextView) root.findViewById(R.id.cart_textView);
        tv.setText("Hovnooo");

        return root;

    }
}
