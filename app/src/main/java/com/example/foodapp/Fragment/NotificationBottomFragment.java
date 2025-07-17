package com.example.foodapp.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodapp.R;
import com.example.foodapp.adapter.NotificationAdapter;
import com.example.foodapp.databinding.FragmentNotificationBottomBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotificationBottomFragment extends BottomSheetDialogFragment {
    private FragmentNotificationBottomBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNotificationBottomBinding.inflate(inflater, container, false);
        List<String> notifications = Arrays.asList(
                "Your order has been Canceled Successfully",
                "Order has been taken by the driver",
                "Congrats Your Order Placed"
        );
        List<Integer> notificationImages = Arrays.asList(
                R.drawable.sademoji,
                R.drawable.truck,
                R.drawable.congrats
        );
        NotificationAdapter adapter = new NotificationAdapter(
                new ArrayList<>(notifications),
                new ArrayList<>(notificationImages)
        );

        binding.notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.notificationRecyclerView.setAdapter(adapter);
        Log.d("NotificationTest", "Notification count: " + adapter.getItemCount());

        return binding.getRoot();
    }
}

