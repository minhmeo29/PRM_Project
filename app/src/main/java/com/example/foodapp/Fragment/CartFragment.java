package com.example.foodapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodapp.PayOutActivity;
import com.example.foodapp.adapter.CartAdapter;
import com.example.foodapp.databinding.FragmentCartBinding;
import com.example.foodapp.model.CartItems;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private FragmentCartBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private List<String> foodNames;
    private List<String> foodPrices;
    private List<String> foodDescriptions;
    private List<String> foodImagesUri;
    private List<String> foodIngredients;
    private List<Integer> quantity;
    private CartAdapter cartAdapter;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();
        retrieveCartItems();

        binding.proceedButton.setOnClickListener(v -> getOrderItemsDetail());
        return binding.getRoot();
    }

    private void getOrderItemsDetail() {
        DatabaseReference orderIdReference = database.getReference()
                .child("users")
                .child(userId)
                .child("CartItems");

        List<String> foodName = new ArrayList<>();
        List<String> foodPrice = new ArrayList<>();
        List<String> foodImage = new ArrayList<>();
        List<String> foodDescription = new ArrayList<>();
        List<String> foodIngredient = new ArrayList<>();
        List<Integer> foodQuantities = cartAdapter.getUpdatedItemsQuantities();

        orderIdReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int index = 0;
                int totalPrice = 0;

                for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
                    CartItems orderItems = foodSnapshot.getValue(CartItems.class);
                    if (orderItems != null) {
                        int quantity = foodQuantities.size() > index ? foodQuantities.get(index) : 1;

                        // Lấy đơn giá
                        int unitPrice = 0;
                        try {
                            unitPrice = Integer.parseInt(orderItems.getFoodPrice().replaceAll("[^\\d]", ""));
                        } catch (Exception ignored) {}

                        totalPrice += quantity * unitPrice;

                        if (orderItems.getFoodName() != null) foodName.add(orderItems.getFoodName());
                        if (orderItems.getFoodPrice() != null) foodPrice.add(orderItems.getFoodPrice());
                        if (orderItems.getFoodDescription() != null) foodDescription.add(orderItems.getFoodDescription());
                        if (orderItems.getFoodImage() != null) foodImage.add(orderItems.getFoodImage());
                        if (orderItems.getFoodngredients() != null) foodIngredient.add(orderItems.getFoodngredients());
                    }
                    index++;
                }

                orderNow(foodName, foodPrice, foodDescription, foodImage, foodIngredient, foodQuantities, totalPrice);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Order making Failed. Please Try Again.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void orderNow(List<String> foodName, List<String> foodPrice, List<String> foodDescription,
                          List<String> foodImage, List<String> foodIngredient,
                          List<Integer> foodQuantities, int totalPrice) {

        if (isAdded() && getContext() != null) {
            Intent intent = new Intent(requireContext(), PayOutActivity.class);
            intent.putStringArrayListExtra("FoodItemName", new ArrayList<>(foodName));
            intent.putStringArrayListExtra("FoodItemPrice", new ArrayList<>(foodPrice));
            intent.putStringArrayListExtra("FoodItemImage", new ArrayList<>(foodImage));
            intent.putStringArrayListExtra("FoodItemDescription", new ArrayList<>(foodDescription));
            intent.putStringArrayListExtra("FoodItemIngredient", new ArrayList<>(foodIngredient));
            intent.putIntegerArrayListExtra("FoodItemQuantities", new ArrayList<>(foodQuantities));
            intent.putExtra("totalPrice", totalPrice + " đ"); // Truyền tổng tiền có định dạng
            startActivity(intent);
        }
    }


    private void retrieveCartItems() {
        database = FirebaseDatabase.getInstance();
        userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "";
        DatabaseReference foodReference = database.getReference().child("users").child(userId).child("CartItems");
        foodNames = new ArrayList<>();
        foodPrices = new ArrayList<>();
        foodDescriptions = new ArrayList<>();
        foodImagesUri = new ArrayList<>();
        foodIngredients = new ArrayList<>();
        quantity = new ArrayList<>();

        foodReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
                    CartItems cartItems = foodSnapshot.getValue(CartItems.class);
                    count++;
                    if (cartItems != null) {
                        android.util.Log.d("CART", "Item " + count + ": " +
                                "foodName=" + cartItems.getFoodName() +
                                ", foodPrice=" + cartItems.getFoodPrice() +
                                ", foodDescription=" + cartItems.getFoodDescription() +
                                ", foodImage=" + cartItems.getFoodImage() +
                                ", foodQuantity=" + cartItems.getFoodQuantity() +
                                ", foodngredients=" + cartItems.getFoodngredients());
                    } else {
                        android.util.Log.d("CART", "Item " + count + ": null");
                    }
                    if (cartItems != null) {
                        if (cartItems.getFoodName() != null) foodNames.add(cartItems.getFoodName());
                        if (cartItems.getFoodPrice() != null) foodPrices.add(cartItems.getFoodPrice());
                        if (cartItems.getFoodDescription() != null) foodDescriptions.add(cartItems.getFoodDescription());
                        if (cartItems.getFoodImage() != null) foodImagesUri.add(cartItems.getFoodImage());
                        if (cartItems.getFoodQuantity() != null) quantity.add(cartItems.getFoodQuantity());
                        if (cartItems.getFoodngredients() != null) foodIngredients.add(cartItems.getFoodngredients());
                    }
                }
                android.util.Log.d("CART", "Total items: " + count);
                android.util.Log.d("CART", "foodNames=" + foodNames.size() + ", foodPrices=" + foodPrices.size() + ", foodDescriptions=" + foodDescriptions.size() + ", foodImagesUri=" + foodImagesUri.size() + ", quantity=" + quantity.size() + ", foodIngredients=" + foodIngredients.size());
                if (!isAdded()) return;
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "data not fetch", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter() {
        cartAdapter = new CartAdapter(
                requireContext(),
                foodNames,
                foodPrices,
                foodDescriptions,
                foodImagesUri,
                quantity,
                foodIngredients
        );
        binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.cartRecyclerView.setAdapter(cartAdapter);
    }
}