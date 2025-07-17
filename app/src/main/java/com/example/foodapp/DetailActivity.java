package com.example.foodapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.foodapp.databinding.ActivityDetailBinding;
import com.example.foodapp.model.CartItems;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private String foodName;
    private String foodImage;
    private String foodDescriptions;
    private String foodIngredients;
    private String foodPrice;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        foodName = intent.getStringExtra("MenuItemName");
        foodDescriptions = intent.getStringExtra("MenuItemDescription");
        foodIngredients = intent.getStringExtra("MenuItemIngredients");
        foodPrice = intent.getStringExtra("MenuItemPrice");
        foodImage = intent.getStringExtra("MenuItemImage");

        binding.detailFoodName.setText(foodName);
        binding.DescriptionTextView.setText(foodDescriptions);
        binding.IngredientsTextView.setText(foodIngredients);
        if (foodImage != null) {
            Glide.with(this).load(Uri.parse(foodImage)).into(binding.detailFoodImage);
        }

        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToCart();
            }
        });
    }

    private void addItemToCart() {
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "";
        CartItems cartItem = new CartItems(
                foodName,
                foodPrice,
                foodDescriptions,
                foodImage,
                1,
                foodIngredients
        );
        FirebaseDatabase.getInstance().getReference()
                .child("user")
                .child(userId)
                .child("CartItems")
                .push()
                .setValue(cartItem)
                .addOnSuccessListener(unused -> Toast.makeText(this, "Items added into cart successFully 😁", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Item Not added 😒", Toast.LENGTH_SHORT).show());
    }
}