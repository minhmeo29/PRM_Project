package com.example.foodapp;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Tìm NavController gắn với fragmentContainerView
        NavController navController = findNavController(this, R.id.fragmentContainerView);

        // Gắn BottomNavigationView với NavController bằng NavigationUI (Java way)
        BottomNavigationView bottomnav = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomnav, navController);
    }
}