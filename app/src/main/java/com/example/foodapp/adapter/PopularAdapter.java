package com.example.foodapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.databinding.PopularItemBinding;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {
    private final List<String> items;
    private final List<String> prices;
    private final List<Integer> images;

    public PopularAdapter(List<String> items, List<String> prices, List<Integer> images) {
        this.items = items;
        this.prices = prices;
        this.images = images;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PopularItemBinding binding = PopularItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PopularViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        holder.bind(items.get(position), prices.get(position), images.get(position));
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
