package com.example.foodapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.databinding.RecentBuyItemBinding;

import java.util.ArrayList;
import com.bumptech.glide.Glide;
public class RecentBuyAdapter extends RecyclerView.Adapter<RecentBuyAdapter.RecentViewHolder> {

    private Context context;
    private ArrayList<String> foodNameList;
    private ArrayList<String> foodImageList;
    private ArrayList<String> foodPriceList;
    private ArrayList<Integer> foodQuantityList;

    public RecentBuyAdapter(Context context,
                            ArrayList<String> foodNameList,
                            ArrayList<String> foodImageList,
                            ArrayList<String> foodPriceList,
                            ArrayList<Integer> foodQuantityList) {
        this.context = context;
        this.foodNameList = foodNameList;
        this.foodImageList = foodImageList;
        this.foodPriceList = foodPriceList;
        this.foodQuantityList = foodQuantityList;
    }

    @NonNull
    @Override
    public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecentBuyItemBinding binding = RecentBuyItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new RecentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return foodNameList.size();
    }

    public class RecentViewHolder extends RecyclerView.ViewHolder {

        private final RecentBuyItemBinding binding;

        public RecentViewHolder(@NonNull RecentBuyItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            binding.foodName.setText(foodNameList.get(position));
            binding.foodPrice.setText(foodPriceList.get(position));
            binding.foodQuantity.setText(String.valueOf(foodQuantityList.get(position)));

            String uriString = foodImageList.get(position);
            Uri uri = Uri.parse(uriString);

            Glide.with(context)
                    .load(uri)
                    .into(binding.foodImage);
        }
    }
}