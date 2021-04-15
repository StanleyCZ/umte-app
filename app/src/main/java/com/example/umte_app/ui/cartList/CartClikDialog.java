package com.example.umte_app.ui.cartList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.umte_app.R;
import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.ui.editCart.EditCartActivity;

public class CartClikDialog extends DialogFragment {

    private Cart cart;

    public CartClikDialog(Cart cart){
        this.cart = cart;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        String[] values = getActivity().getResources().getStringArray(R.array.basket_dialog_values);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Co chcete udělat?")
                .setItems(R.array.basket_dialog_values, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if(which == 0){
                            Intent intent = new Intent(getContext(), EditCartActivity.class);
                            intent.putExtra("cart-to-edit",cart);
                            startActivity(intent);
                        }
                        else if(which == 1){
                            Toast.makeText(getContext(),"TODO NÁKUP",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        return builder.create();
    }
}
