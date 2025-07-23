package com.example.foodapp.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bumptech.glide.Glide;
import com.example.foodapp.R;
import com.example.foodapp.RecentOrderItems;
import com.example.foodapp.adapter.BuyAgainAdapter;
import com.example.foodapp.databinding.FragmentHistoryBinding;
import com.example.foodapp.model.OrderDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.Collections;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private BuyAgainAdapter buyAgainAdapter;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private String userId;
    private ArrayList<OrderDetails> listOfOrderItem = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        retrieveBuyHistory();

        binding.recentbuyitem.setOnClickListener(v -> seeItemsRecentBuy());

        binding.receivedButton.setOnClickListener(v -> updateOrderStatus());

        return binding.getRoot();
    }

    private void updateOrderStatus() {
        if (listOfOrderItem.isEmpty()) return;
        String itemPushKey = listOfOrderItem.get(0).getItemPushKey();
        if (itemPushKey != null && userId != null && !userId.isEmpty()) {
            // Cập nhật paymentReceived = true ở CompletedOrder
            database.getReference()
                .child("CompletedOrder").child(itemPushKey).child("paymentReceived")
                .setValue(true);

            // (Có thể giữ lại hoặc bỏ cập nhật orderAccepted tuỳ vào luồng mới)
            // database.getReference()
            //     .child("users").child(userId).child("BuyHistory").child(itemPushKey).child("orderAccepted")
            //     .setValue(false);
            // database.getReference()
            //     .child("OrderDetails").child(itemPushKey).child("orderAccepted")
            //     .setValue(false);

            // UI
            binding.receivedButton.setVisibility(View.GONE);
            binding.orderdStutus.getBackground().setTint(Color.GREEN);
        }
    }

    private void seeItemsRecentBuy() {
        if (!listOfOrderItem.isEmpty()) {
            Intent intent = new Intent(requireContext(), RecentOrderItems.class);
            intent.putExtra("RecentBuyOrderItem", listOfOrderItem);
            startActivity(intent);
        }
    }

    private void retrieveBuyHistory() {
        userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "";

        DatabaseReference buyItemReference = database.getReference()
                .child("users").child(userId).child("BuyHistory");

        Query sortingQuery = buyItemReference.orderByChild("currentTime");

        sortingQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot buySnapshot : snapshot.getChildren()) {
                    OrderDetails buyHistoryItem = buySnapshot.getValue(OrderDetails.class);
                    if (buyHistoryItem != null) {
                        listOfOrderItem.add(buyHistoryItem);
                    }
                }

                Collections.reverse(listOfOrderItem);

                if (!listOfOrderItem.isEmpty()) {
                    setDataInRecentBuyItem();
                    setPreviousBuyItemsRecyclerView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // handle error
            }
        });
    }

    private void setDataInRecentBuyItem() {
        if (!listOfOrderItem.isEmpty()) {
            OrderDetails recentOrderItem = listOfOrderItem.get(0);

            binding.buyAgainFoodName.setText(
                    recentOrderItem.getFoodNames() != null && !recentOrderItem.getFoodNames().isEmpty()
                            ? recentOrderItem.getFoodNames().get(0) : "");

            binding.buyAgainFoodPrice.setText(
                    recentOrderItem.getFoodPrices() != null && !recentOrderItem.getFoodPrices().isEmpty()
                            ? recentOrderItem.getFoodPrices().get(0) : "");

            String image = (recentOrderItem.getFoodImages() != null && !recentOrderItem.getFoodImages().isEmpty())
                    ? recentOrderItem.getFoodImages().get(0) : "";

            Glide.with(requireContext()).load(image).into(binding.buyAgainFoodImage);

            boolean isOrderAccepted = recentOrderItem.isOrderAccepted();
            if (isOrderAccepted) {
                binding.orderdStutus.getBackground().setTint(Color.GREEN);
                binding.receivedButton.setVisibility(View.VISIBLE);
            } else {
                binding.receivedButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void setPreviousBuyItemsRecyclerView() {
        ArrayList<String> buyAgainFoodName = new ArrayList<>();
        ArrayList<String> buyAgainFoodPrice = new ArrayList<>();
        ArrayList<String> buyAgainFoodImage = new ArrayList<>();

        for (int i = 1; i < listOfOrderItem.size(); i++) {
            OrderDetails item = listOfOrderItem.get(i);

            if (item.getFoodNames() != null && !item.getFoodNames().isEmpty()) {
                buyAgainFoodName.add(item.getFoodNames().get(0));
                if (item.getFoodPrices() != null && !item.getFoodPrices().isEmpty()) {
                    buyAgainFoodPrice.add(item.getFoodPrices().get(0));
                }
                if (item.getFoodImages() != null && !item.getFoodImages().isEmpty()) {
                    buyAgainFoodImage.add(item.getFoodImages().get(0));
                }
            }
        }

        binding.BuyAgainRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        buyAgainAdapter = new BuyAgainAdapter(
                buyAgainFoodName, buyAgainFoodPrice, buyAgainFoodImage, requireContext()
        );
        binding.BuyAgainRecyclerView.setAdapter(buyAgainAdapter);
    }
}
