package com.example.foodapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodapp.R;
import com.example.foodapp.adapter.MenuAdapter;
import com.example.foodapp.databinding.FragmentSearchBinding;
import com.example.foodapp.model.MenuItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private MenuAdapter adapter;
    private FirebaseDatabase database;
    private final List<MenuItem> originalMenuItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        retrieveMenuItem();
        setupSearchView();
        return binding.getRoot();
    }

    private void retrieveMenuItem() {
        DatabaseReference menuRef = FirebaseDatabase.getInstance().getReference("menu");
        originalMenuItems.clear();
        menuRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    MenuItem menuItem = itemSnapshot.getValue(MenuItem.class);
                    if (menuItem != null) {
                        originalMenuItems.add(menuItem);
                    }
                }
                showAllMenu();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void showAllMenu() {
        List<MenuItem> filteredMenuItem = new ArrayList<>(originalMenuItems);
        setAdapter(filteredMenuItem);
    }

    private void setAdapter(List<MenuItem> filteredMenuItem) {
        adapter = new MenuAdapter(filteredMenuItem, requireContext());
        binding.menuRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.menuRecyclerView.setAdapter(adapter);
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
        List<MenuItem> filteredMenuItems = new ArrayList<>();
        for (MenuItem item : originalMenuItems) {
            if (item.getFoodName() != null && item.getFoodName().toLowerCase().contains(query.toLowerCase())) {
                filteredMenuItems.add(item);
            }
        }
        setAdapter(filteredMenuItems);
    }
}

