package com.example.foodapp.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodapp.R;
import com.example.foodapp.adapter.MenuAdapter;
import com.example.foodapp.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private MenuAdapter adapter;
    private final List<String> originalMenuFoodName = Arrays.asList(
            "Burger", "Sandwich", "Momo",
            "Item", "Momo", "Sandwich"
    );

    private final List<String> originalMenuItemPrice = Arrays.asList(
            "10,000VND",
            "20,000VND",
            "30,000VND",
            "10,000VND",
            "20,000VND",
            "30,000VND"
    );

    private final List<Integer> originalMenuImage = Arrays.asList(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu6
    );

    private List<String> filteredMenuFoodName = new ArrayList<>();
    private List<String> filteredMenuItemPrice = new ArrayList<>();
    private List<Integer> filteredMenuImage = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        adapter = new MenuAdapter(filteredMenuFoodName, filteredMenuItemPrice, filteredMenuImage, requireContext());


        binding.menuRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.menuRecyclerView.setAdapter(adapter);

        // set up for search view
        setupSearchView();
        // show all menu items
        showAllMenuItems();
        return binding.getRoot();
    }

    private void setupSearchView() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterMenuItems(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterMenuItems(newText);
                return true;
            }
        });
    }

    private void filterMenuItems(String query) {
        filteredMenuFoodName.clear();
        filteredMenuItemPrice.clear();
        filteredMenuImage.clear();

        for (int index = 0; index < originalMenuFoodName.size(); index++) {
            String foodName = originalMenuFoodName.get(index);
            if (foodName.toLowerCase().contains(query.toLowerCase())) {
                filteredMenuFoodName.add(foodName);
                filteredMenuItemPrice.add(originalMenuItemPrice.get(index));
                filteredMenuImage.add(originalMenuImage.get(index));
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void showAllMenuItems() {
        filteredMenuFoodName.clear();
        filteredMenuItemPrice.clear();
        filteredMenuImage.clear();

        filteredMenuFoodName.addAll(originalMenuFoodName);
        filteredMenuItemPrice.addAll(originalMenuItemPrice);
        filteredMenuImage.addAll(originalMenuImage);

        adapter.notifyDataSetChanged();

    }
}

