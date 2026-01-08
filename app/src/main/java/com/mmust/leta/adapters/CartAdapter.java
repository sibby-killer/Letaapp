package com.mmust.leta.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mmust.leta.databinding.ItemCartBinding;
import com.mmust.leta.models.CartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for Cart Items
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    
    private List<CartItem> items = new ArrayList<>();
    private OnCartItemListener listener;
    
    public interface OnCartItemListener {
        void onQuantityChanged(CartItem item, int newQuantity);
        void onItemRemoved(CartItem item);
    }
    
    public CartAdapter(OnCartItemListener listener) {
        this.listener = listener;
    }
    
    public void setItems(List<CartItem> items) {
        this.items = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding binding = ItemCartBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new CartViewHolder(binding);
    }
    
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bind(items.get(position));
    }
    
    @Override
    public int getItemCount() {
        return items.size();
    }
    
    class CartViewHolder extends RecyclerView.ViewHolder {
        private ItemCartBinding binding;
        
        CartViewHolder(ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        
        void bind(CartItem item) {
            // TODO: Bind cart item data to views
            // binding.tvItemName.setText(item.getName());
            // binding.tvItemPrice.setText(String.format("KES %.2f", item.getPrice()));
            // binding.tvQuantity.setText(String.valueOf(item.getQuantity()));
        }
    }
}
