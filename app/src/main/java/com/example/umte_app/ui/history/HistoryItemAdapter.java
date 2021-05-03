package com.example.umte_app.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umte_app.R;
import com.example.umte_app.models.entities.CartItem;
import com.example.umte_app.models.entities.CartWithItems;
import com.example.umte_app.ui.cartList.CartAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.HistoryItemHolder> {

    private List<CartWithItems> historyItems = new ArrayList<>();

    @NonNull
    @Override
    public HistoryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item,parent,false);
        return new HistoryItemAdapter.HistoryItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemHolder holder, int position) {

        CartWithItems cart = historyItems.get(position);
        List<CartItem> buyedItems = cart.items.stream().filter(cartItem -> cartItem.isPurchased == true).collect(Collectors.toList());

        int totalProductCount = buyedItems.stream().mapToInt(cartItem -> cartItem.count).sum();
        float totalPrice = 0f;
        for (CartItem item : buyedItems) {
            totalPrice += (float)item.count * item.price;
        }
        holder.tvCartName.setText(cart.cart.name);
        holder.tvShopName.setText(cart.cart.storeName);
        holder.tvTotalCount.setText(String.valueOf(totalProductCount));
        holder.tvTotalPrice.setText(String.valueOf(totalPrice));

    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    public void setCarts(List<CartWithItems> carts){
        this.historyItems = carts;
        notifyDataSetChanged();
    }


    class HistoryItemHolder extends RecyclerView.ViewHolder {

        private TextView tvCartName;
        private TextView tvShopName;
        private TextView tvTotalCount;
        private TextView tvTotalPrice;

        public HistoryItemHolder(@NonNull View itemView) {
            super(itemView);

            tvCartName = itemView.findViewById(R.id.HISTcartName);
            tvShopName = itemView.findViewById(R.id.HISTshopName);
            tvTotalCount = itemView.findViewById(R.id.HISTtotalCount);
            tvTotalPrice = itemView.findViewById(R.id.HISTtotalPrice);
        }
    }
}
