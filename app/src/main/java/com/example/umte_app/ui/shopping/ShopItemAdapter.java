package com.example.umte_app.ui.shopping;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umte_app.R;
import com.example.umte_app.models.entities.CartItem;
import com.example.umte_app.models.entities.CartWithItems;
import com.example.umte_app.ui.cartList.CartAdapter;
import com.example.umte_app.ui.detailCart.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.ShopItemHolder> {

    private List<CartItem> shopItems = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ShopItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_item,parent,false);
        return new ShopItemAdapter.ShopItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemHolder holder, int position) {

        CartItem shopItem = shopItems.get(position);
        holder.tvName.setText(shopItem.name);
        holder.tvCount.setText(String.valueOf(shopItem.count) + " ks");
        holder.tvPrice.setText(shopItem.price != 0f ? String.valueOf(shopItem.price) + " Kč/ks": "Cena neuvedena");
        holder.cardView.setCardBackgroundColor(shopItem.isPurchased ? Color.parseColor("#00ff00") : Color.parseColor("#ff0000"));

    }

    @Override
    public int getItemCount() {
        return shopItems.size();
    }
    public void setCart(List<CartItem> items){
        this.shopItems = items;
        notifyDataSetChanged();
    }
    public CartItem getShopItemAt(int position){
        return shopItems.get(position);
    }

    class ShopItemHolder extends RecyclerView.ViewHolder{

        private TextView tvName;
        private TextView tvCount;
        private TextView tvPrice;
        private CardView cardView;

        public ShopItemHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.shoppingCardView);
            tvName = itemView.findViewById(R.id.ShopItem_productName);
            tvCount = itemView.findViewById(R.id.ShopItem_ProductCount);
            tvPrice = itemView.findViewById(R.id.ShopItem_productPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(shopItems.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(CartItem item);
    }
    public void setOnItemClickListener(ShopItemAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

}
