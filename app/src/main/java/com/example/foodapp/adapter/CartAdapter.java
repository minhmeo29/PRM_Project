package com.example.foodapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.databinding.CartItemBinding;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private final ArrayList<String> cartItems;
    private final ArrayList<String> cartItemPrice;
    private final ArrayList<Integer> cartImage;
    private final ArrayList<Integer> itemQuantities;

    public CartAdapter(ArrayList<String> cartItems, ArrayList<String> cartItemPrice, ArrayList<Integer> cartImage) {
        this.cartItems = cartItems != null ? cartItems : new ArrayList<>();
        this.cartItemPrice = cartItemPrice != null ? cartItemPrice : new ArrayList<>();
        this.cartImage = cartImage != null ? cartImage : new ArrayList<>();
        this.itemQuantities = new ArrayList<>();
        initializeQuantities();
    }

    private void initializeQuantities() {
        for (int i = 0; i < cartItems.size(); i++) {
            itemQuantities.add(1);
        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartItemBinding binding = CartItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartViewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bind(cartItems.get(position), cartItemPrice.get(position), cartImage.get(position), itemQuantities.get(position));
    }

    @Override
    public int getItemCount() {
        return cartItems != null ? cartItems.size() : 0;
    }

    public void decreaseQuantity(int position) {
        if (itemQuantities.get(position) > 1) {
            itemQuantities.set(position, itemQuantities.get(position) - 1);
            notifyItemChanged(position);
        } else {
            deleteItem(position); // Xóa item nếu số lượng giảm xuống 1 hoặc nhỏ hơn
        }
    }

    public void increaseQuantity(int position) {
        if (itemQuantities.get(position) < 10) {
            itemQuantities.set(position, itemQuantities.get(position) + 1);
            notifyItemChanged(position);
        }
    }

    public void deleteItem(int position) {
        if (position >= 0 && position < cartItems.size()) {
            cartItems.remove(position);
            cartItemPrice.remove(position);
            cartImage.remove(position);
            itemQuantities.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        private final CartItemBinding binding;
        private final CartAdapter adapter;

        public CartViewHolder(CartItemBinding binding, CartAdapter adapter) {
            super(binding.getRoot());
            this.binding = binding;
            this.adapter = adapter;

            binding.minusbutton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    adapter.decreaseQuantity(position);
                }
            });

            binding.plusbutton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    adapter.increaseQuantity(position);
                }
            });

            binding.deleteButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    adapter.deleteItem(position);
                }
            });
        }

        public void bind(String item, String price, Integer imageResId, Integer quantity) {
            binding.cartitemQuantity.setText(String.valueOf(quantity));
            binding.cartFoodName.setText(item);
            binding.cartitemPrice.setText(price);
            binding.cartimage.setImageResource(imageResId);
        }
    }
}