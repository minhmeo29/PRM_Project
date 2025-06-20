package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        new android.os.Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(Splash_Screen.this, StartActivity.class));
            finish();
        }, 3000);

    }
}