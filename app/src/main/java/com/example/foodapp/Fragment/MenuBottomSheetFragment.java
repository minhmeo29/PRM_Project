package com.example.foodapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.adapter.MenuAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.foodapp.model.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuBottomSheetFragment extends BottomSheetDialogFragment {
    private ImageButton buttonBack;
    private RecyclerView menuRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_bottom_sheet, container, false);
        buttonBack = view.findViewById(R.id.buttonBack);
        menuRecyclerView = view.findViewById(R.id.menuRecyclerView);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // Lấy menu từ Firebase
        DatabaseReference menuRef = FirebaseDatabase.getInstance().getReference("menu");
        List<MenuItem> menuItems = new ArrayList<>();
        menuRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                menuItems.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    MenuItem item = itemSnapshot.getValue(MenuItem.class);
                    if (item != null) menuItems.add(item);
                }
                MenuAdapter adapter = new MenuAdapter(menuItems, requireContext());
                menuRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                menuRecyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        return view;
    }
} 