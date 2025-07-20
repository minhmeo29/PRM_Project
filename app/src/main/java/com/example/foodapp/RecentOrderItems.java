package com.example.foodapp;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodapp.adapter.RecentBuyAdapter;
import com.example.foodapp.databinding.ActivityRecentOrderItemsBinding;
import com.example.foodapp.model.OrderDetails;

import java.util.ArrayList;


public class RecentOrderItems extends AppCompatActivity {

    private ActivityRecentOrderItemsBinding binding;

    private ArrayList<String> allFoodNames;
    private ArrayList<String> allFoodImages;
    private ArrayList<String> allFoodPrices;
    private ArrayList<Integer> allFoodQuantities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecentOrderItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Back button
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayList<OrderDetails> recentOrderItems =
                (ArrayList<OrderDetails>) getIntent().getSerializableExtra("RecentBuyOrderItem");

        if (recentOrderItems != null && !recentOrderItems.isEmpty()) {
            OrderDetails recentOrderItem = recentOrderItems.get(0);

            allFoodNames = new ArrayList<>(recentOrderItem.getFoodNames());
            allFoodImages = new ArrayList<>(recentOrderItem.getFoodImages());
            allFoodPrices = new ArrayList<>(recentOrderItem.getFoodPrices());
            allFoodQuantities = new ArrayList<>(recentOrderItem.getFoodQuantities());
        } else {
            allFoodNames = new ArrayList<>();
            allFoodImages = new ArrayList<>();
            allFoodPrices = new ArrayList<>();
            allFoodQuantities = new ArrayList<>();
        }

        setAdapter();
    }

    private void setAdapter() {
        binding.recyclerViewRecentBuy.setLayoutManager(new LinearLayoutManager(this));
        RecentBuyAdapter adapter = new RecentBuyAdapter(
                this,
                allFoodNames,
                allFoodImages,
                allFoodPrices,
                allFoodQuantities
        );
        binding.recyclerViewRecentBuy.setAdapter(adapter);
    }
}