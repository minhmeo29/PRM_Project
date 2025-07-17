package com.example.foodapp;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.foodapp.Fragment.NotificationBottomFragment;
import com.example.foodapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Find NavController associated with fragmentContainerView
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);

        // Attach BottomNavigationView to NavController using NavigationUI (Java way)
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNav, navController);

        binding.notificationButton.setOnClickListener(v -> {
            NotificationBottomFragment bottomSheetDialog = new NotificationBottomFragment();
            bottomSheetDialog.show(getSupportFragmentManager(), "NotificationBottomSheet");
        });
    }
}