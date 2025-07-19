package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.databinding.ActivityPayOutBinding;
import com.example.foodapp.model.OrderDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PayOutActivity extends AppCompatActivity {

    ActivityPayOutBinding binding;
    ArrayList<String> foodNames, foodPrices, foodImages;
    ArrayList<Integer> foodQuantities;
    String userUid, totalPrice;
    long currentTime;
    FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        foodNames = intent.getStringArrayListExtra("FoodItemName");
        foodPrices = intent.getStringArrayListExtra("FoodItemPrice");
        foodImages = intent.getStringArrayListExtra("FoodItemImage");
        foodQuantities = intent.getIntegerArrayListExtra("FoodItemQuantities");

        userUid = intent.getStringExtra("userUid");
        totalPrice = intent.getStringExtra("totalPrice");
        currentTime = System.currentTimeMillis();

        // Hiển thị tổng tiền
        String totalPrice = getIntent().getStringExtra("totalPrice");
        binding.totalAmount.setText(totalPrice);

        // Nút Đặt đồ
        binding.orderButton.setOnClickListener(view -> {
            String name = binding.name.getText().toString().trim();
            String address = binding.address.getText().toString().trim();
            String phone = binding.phone.getText().toString().trim();

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ Tên, Địa chỉ và Số điện thoại!", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference reference = database.getReference("OrderDetails").push();
            String itemPushKey = reference.getKey();

            OrderDetails order = new OrderDetails(
                    userUid,
                    name,
                    foodNames,
                    foodPrices,
                    foodImages,
                    foodQuantities,
                    address,
                    totalPrice,
                    phone,
                    currentTime,
                    itemPushKey,
                    false,
                    false
            );

            reference.setValue(order).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(PayOutActivity.this, MainActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                } else {
                    Toast.makeText(this, "Lỗi đặt hàng!", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Nút quay lại
        binding.button9.setOnClickListener(view -> finish());
    }
}
