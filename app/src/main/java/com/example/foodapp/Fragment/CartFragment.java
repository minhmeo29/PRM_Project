package com.example.foodapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodapp.R;
import com.example.foodapp.adapter.CartAdapter;
import com.example.foodapp.databinding.FragmentCartBinding;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private FragmentCartBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ArrayList<String> cartFoodName = new ArrayList<>();
        cartFoodName.add("Burger");
        cartFoodName.add("Pizza");
        cartFoodName.add("Steak");
        cartFoodName.add("Sandwich");
        cartFoodName.add("Momo");

        ArrayList<String> cartItemPrice = new ArrayList<>();
        cartItemPrice.add("$5");
        cartItemPrice.add("$8");
        cartItemPrice.add("$10");
        cartItemPrice.add("$6");
        cartItemPrice.add("$4");

        ArrayList<Integer> cartImage = new ArrayList<>();
        cartImage.add(R.drawable.menu1);
        cartImage.add(R.drawable.menu2);
        cartImage.add(R.drawable.menu3);
        cartImage.add(R.drawable.menu4);
        cartImage.add(R.drawable.menu5);

        CartAdapter adapter = new CartAdapter(cartFoodName, cartItemPrice, cartImage);
        binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.cartRecyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}