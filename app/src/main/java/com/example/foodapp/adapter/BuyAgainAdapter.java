package com.example.foodapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.databinding.BuyAgainItemBinding;
//import com.example.wavesoffood.databinding.BuyAgainItemBinding;

import java.util.ArrayList;

public class BuyAgainAdapter extends RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder> {
    private ArrayList<String> buyAgainFoodName;
    private ArrayList<String> buyAgainFoodPrice;
    private ArrayList<Integer> buyAgainFoodImage;

    public BuyAgainAdapter(ArrayList<String> buyAgainFoodName, ArrayList<String> buyAgainFoodPrice, ArrayList<Integer> buyAgainFoodImage) {
        this.buyAgainFoodName = buyAgainFoodName;
        this.buyAgainFoodPrice = buyAgainFoodPrice;
        this.buyAgainFoodImage = buyAgainFoodImage;
    }

    @Override
    public void onBindViewHolder(BuyAgainViewHolder holder, int position) {
        holder.bind(buyAgainFoodName.get(position), buyAgainFoodPrice.get(position), buyAgainFoodImage.get(position));
    }

    @Override
    public BuyAgainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        com.example.foodapp.databinding.BuyAgainItemBinding binding = BuyAgainItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BuyAgainViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return buyAgainFoodName.size();
    }

    public static class BuyAgainViewHolder extends RecyclerView.ViewHolder {
        private BuyAgainItemBinding binding;

        public BuyAgainViewHolder(BuyAgainItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String foodName, String foodPrice, int foodImage) {
            binding.buyAgainFoodName.setText(foodName);
            binding.buyAgainFoodPrice.setText(foodPrice);
            binding.buyAgainFoodImage.setImageResource(foodImage);
        }
    }
}