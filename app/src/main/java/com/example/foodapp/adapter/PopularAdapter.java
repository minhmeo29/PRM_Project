package com.example.foodapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.DetailActivity;
import com.example.foodapp.databinding.PopularItemBinding;

import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {

    private final List<String> items;
    private final List<String> prices;
    private final List<Integer> images;
    private final List<String> descriptions;
    private final List<String> ingredients;
    private final Context context; // <- tương đương requireContext

    public PopularAdapter(Context context, List<String> items, List<String> prices, List<Integer> images, List<String> descriptions, List<String> ingredients) {
        this.context = context;
        this.items = items;
        this.prices = prices;
        this.images = images;
        this.descriptions = descriptions;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PopularItemBinding binding = PopularItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PopularViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        String item = items.get(position);
        String price = prices.get(position);
        int imageResId = images.get(position);

        holder.bind(item, price, imageResId);

        // Xử lý khi click vào item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("MenuItemName", item); // Truyền tên món ăn
            intent.putExtra("MenuItemPrice", price); // Truyền giá
            intent.putExtra("MenuItemImage", "android.resource://" + context.getPackageName() + "/" + imageResId); // Truyền đúng uri resource
            intent.putExtra("MenuItemDescription", descriptions.get(position)); // Truyền mô tả
            intent.putExtra("MenuItemIngredients", ingredients.get(position)); // Truyền nguyên liệu
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class PopularViewHolder extends RecyclerView.ViewHolder {
        private final PopularItemBinding binding;

        public PopularViewHolder(PopularItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String item, String price, int imageResId) {
            binding.foodNamePopular.setText(item);
            binding.pricePopular.setText(price);
            binding.imageView5.setImageResource(imageResId);
        }
    }
}
