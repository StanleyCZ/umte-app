package com.example.umte_app.ui.carts;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umte_app.R;
import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.models.entities.CartWithItems;

import java.util.ArrayList;
import java.util.List;

public class  CartAdapter  extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    private List<Cart> carts = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item,parent,false);
        return new CartHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
            Cart currentCart = carts.get(position);
            holder.tvCartName.setText(currentCart.name);
            holder.tvShopName.setText(currentCart.storeName);
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public void setCarts(List<Cart> carts){
        this.carts = carts;
        notifyDataSetChanged();
    }

    public Cart getCartAt(int position){
        return carts.get(position);
    }

    class CartHolder extends RecyclerView.ViewHolder{
        private TextView tvCartName;
        private TextView tvShopName;

        public CartHolder(@NonNull View itemView) {
            super(itemView);
            tvCartName = itemView.findViewById(R.id.basketItem_name);
            tvShopName = itemView.findViewById(R.id.basketItem_shopName);

            //pro nastaveni udalosti, kdyz uzivatel klikne na kosik
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(carts.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Cart cart);

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
