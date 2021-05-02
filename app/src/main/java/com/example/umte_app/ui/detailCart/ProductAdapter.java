package com.example.umte_app.ui.detailCart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umte_app.R;
import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.models.entities.CartItem;
import com.example.umte_app.models.entities.CartWithItems;
import com.example.umte_app.ui.cartList.CartAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private List<CartItem> items = new ArrayList<>();

    private OnItemClickListener listener;

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_product_item,parent,false);
        return new ProductAdapter.ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        CartItem currentItem = items.get(position);
        holder.productName_textView.setText(currentItem.name);
        holder.productPrice_textView.setText(currentItem.price != 0f ? String.valueOf(currentItem.price) + " Kč/ks": "Cena neuvedena");
        holder.productCount_textView.setText(String.valueOf(currentItem.count) + " ks");
        //TODO OBRAZEK

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public CartItem getProductAt(int position){
        return items.get(position);
    }

    public void setProducts(List<CartItem> products){
        this.items = products;
        notifyDataSetChanged();
    }

    class ProductHolder extends RecyclerView.ViewHolder{
        private TextView productName_textView;
        private TextView productPrice_textView;
        private TextView productCount_textView;
        private ImageView product_imageView;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            productName_textView = itemView.findViewById(R.id.basketDetail_productName);
            productPrice_textView = itemView.findViewById(R.id.basketDetail_productPrice);
            productCount_textView = itemView.findViewById(R.id.basketDetai_ProductCount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(items.get(position));
                    }
                }
            });
        }

    }
    public interface OnItemClickListener{
        void onItemClick(CartItem product);

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
