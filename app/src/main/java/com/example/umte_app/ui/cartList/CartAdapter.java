package com.example.umte_app.ui.cartList;


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

    private List<CartWithItems> carts = new ArrayList<>();
    private OnItemClickListener listener;
    private OnItemLongClickListener longListener;

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item,parent,false);
        return new CartHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
            CartWithItems currentCart = carts.get(position);
            int productCount = currentCart.items.size();
            holder.tvCartName.setText(currentCart.cart.name);
            holder.tvShopName.setText(currentCart.cart.storeName);
            holder.tvProductCount.setText(String.valueOf(productCount));
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public void setCarts(List<CartWithItems> carts){
        this.carts = carts;
        notifyDataSetChanged();
    }

    public CartWithItems getCartAt(int position){
        return carts.get(position);
    }

    class CartHolder extends RecyclerView.ViewHolder{
        private TextView tvCartName;
        private TextView tvShopName;
        private TextView tvProductCount;

        public CartHolder(@NonNull View itemView) {
            super(itemView);
            tvCartName = itemView.findViewById(R.id.basketItem_name);
            tvShopName = itemView.findViewById(R.id.basketItem_shopName);
            tvProductCount = itemView.findViewById(R.id.basketItem_productCount);

            //pro nastaveni udalosti, kdyz uzivatel klikne na kosik
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(carts.get(position).cart);
                    }
                }
            });
            //pro nastaveni udalosti, kdyz dlouho stiskne kosik
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if(longListener != null && position != RecyclerView.NO_POSITION){
                        longListener.onItemLongClick(carts.get(position).cart);
                        return true;
                    } return false;
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

    public interface OnItemLongClickListener{
        void onItemLongClick(Cart cart);
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        this.longListener = listener;
    }

}
