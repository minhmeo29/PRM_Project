package com.example.foodapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.model.MenuItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SeedMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btnSeed = new Button(this);
        btnSeed.setText("Seed Menu Data");
        setContentView(btnSeed);
        btnSeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seedMenuData();
            }
        });
    }

    private void seedMenuData() {
        DatabaseReference menuRef = FirebaseDatabase.getInstance().getReference("menu");
        menuRef.push().setValue(new MenuItem("Burger", "10,000 VND", "Bánh mì kẹp thịt bò, rau, phô mai", "android.resource://com.example.foodapp/drawable/menu1", "Thịt bò, rau, phô mai"));
        menuRef.push().setValue(new MenuItem("Pizza", "20,000 VND", "Pizza Ý truyền thống", "android.resource://com.example.foodapp/drawable/menu2", "Bột mì, phô mai, sốt cà chua"));
        menuRef.push().setValue(new MenuItem("Sandwich", "15,000 VND", "Bánh mì sandwich kẹp trứng", "android.resource://com.example.foodapp/drawable/menu3", "Bánh mì, trứng, rau"));
        menuRef.push().setValue(new MenuItem("Momo", "12,000 VND", "Bánh bao hấp kiểu Nepal", "android.resource://com.example.foodapp/drawable/menu4", "Bột mì, thịt, rau"));
        menuRef.push().setValue(new MenuItem("Steak", "50,000 VND", "Bít tết bò Mỹ", "android.resource://com.example.foodapp/drawable/menu5", "Thịt bò, tiêu, sốt"));
        Toast.makeText(this, "Đã seed menu mẫu lên Firebase!", Toast.LENGTH_SHORT).show();
    }
} 