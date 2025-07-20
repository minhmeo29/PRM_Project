package com.example.foodapp.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.databinding.BuyAgainItemBinding;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
public class BuyAgainAdapter extends RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder> {

    private final List<String> buyAgainFoodName;
    private final List<String> buyAgainFoodPrice;
    private final List<String> buyAgainFoodImage;
    private final Context context;

    public BuyAgainAdapter(List<String> buyAgainFoodName,
                           List<String> buyAgainFoodPrice,
                           List<String> buyAgainFoodImage,
                           Context context) {
        this.buyAgainFoodName = buyAgainFoodName;
        this.buyAgainFoodPrice = buyAgainFoodPrice;
        this.buyAgainFoodImage = buyAgainFoodImage;
        this.context = context;
    }

    @NonNull
    @Override
    public BuyAgainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BuyAgainItemBinding binding = BuyAgainItemBinding.inflate(inflater, parent, false);
        return new BuyAgainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyAgainViewHolder holder, int position) {
        holder.bind(
                buyAgainFoodName.get(position),
                buyAgainFoodPrice.get(position),
                buyAgainFoodImage.get(position)
        );
    }

    @Override
    public int getItemCount() {
        return buyAgainFoodName.size();
    }

    class BuyAgainViewHolder extends RecyclerView.ViewHolder {
        private final BuyAgainItemBinding binding;

        public BuyAgainViewHolder(BuyAgainItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String foodName, String foodPrice, String foodImage) {
            binding.buyAgainFoodName.setText(foodName);
            binding.buyAgainFoodPrice.setText(foodPrice);

            Uri uri = Uri.parse(foodImage);
            Glide.with(context)
                    .load(uri)
                    .into(binding.buyAgainFoodImage);
        }
    }
}