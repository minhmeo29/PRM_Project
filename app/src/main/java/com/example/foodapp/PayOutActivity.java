package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.databinding.ActivityPayOutBinding;
import com.example.foodapp.model.OrderDetails;
import com.example.foodapp.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PayOutActivity extends AppCompatActivity {

    ActivityPayOutBinding binding;
    ArrayList<String> foodNames, foodPrices, foodImages;
    ArrayList<Integer> foodQuantities;
    String totalPrice;
    long currentTime;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        currentTime = System.currentTimeMillis();

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        foodNames = intent.getStringArrayListExtra("FoodItemName");
        foodPrices = intent.getStringArrayListExtra("FoodItemPrice");
        foodImages = intent.getStringArrayListExtra("FoodItemImage");
        foodQuantities = intent.getIntegerArrayListExtra("FoodItemQuantities");
        totalPrice = intent.getStringExtra("totalPrice");

        // Hiển thị tổng tiền
        binding.totalAmount.setText(totalPrice);

        // Lấy userId từ FirebaseAuth
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

        // Load thông tin user từ Firebase
        if (userId != null) {
            DatabaseReference userRef = database.getReference("users").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    UserModel user = snapshot.getValue(UserModel.class);
                    if (user != null) {
                        if (user.getName() != null) binding.name.setText(user.getName());
                        if (user.getAddress() != null) binding.address.setText(user.getAddress());
                        if (user.getPhone() != null) binding.phone.setText(user.getPhone());
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(PayOutActivity.this, "Không thể tải thông tin người dùng!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Nút Đặt hàng
        binding.orderButton.setOnClickListener(view -> {
            String name = binding.name.getText().toString().trim();
            String address = binding.address.getText().toString().trim();
            String phone = binding.phone.getText().toString().trim();

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ Tên, Địa chỉ và Số điện thoại!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (userId == null) {
                Toast.makeText(this, "Không xác định được người dùng!", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference orderRef = database.getReference("OrderDetails").push();
            String itemPushKey = orderRef.getKey();

            // Tạo đối tượng OrderDetails
            OrderDetails order = new OrderDetails(
                    userId,
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

            // Ghi vào "OrderDetails"
            orderRef.setValue(order).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Ghi lịch sử vào "BuyHistory"
                    DatabaseReference buyHistoryRef = database.getReference("users")
                            .child(userId)
                            .child("BuyHistory")
                            .child(itemPushKey);

                    Map<String, Object> buyHistoryMap = new HashMap<>();
                    buyHistoryMap.put("userUid", userId);
                    buyHistoryMap.put("userName", name);
                    buyHistoryMap.put("foodNames", foodNames);
                    buyHistoryMap.put("foodPrices", foodPrices);
                    buyHistoryMap.put("foodImages", foodImages);
                    buyHistoryMap.put("foodQuantities", foodQuantities);
                    buyHistoryMap.put("address", address);
                    buyHistoryMap.put("totalPrice", totalPrice);
                    buyHistoryMap.put("phoneNumber", phone);
                    buyHistoryMap.put("currentTime", currentTime);
                    buyHistoryMap.put("itemPushKey", itemPushKey);
                    buyHistoryMap.put("orderAccepted", false);
                    buyHistoryMap.put("paymentReceived", false);
                    buyHistoryMap.put("AcceptedOrder", false);

                    buyHistoryRef.setValue(buyHistoryMap);

                    // Xoá giỏ hàng
                    database.getReference("users").child(userId).child("CartItems").removeValue()
                            .addOnCompleteListener(cartClearTask -> {
                                if (cartClearTask.isSuccessful()) {
                                    Toast.makeText(PayOutActivity.this, "Đặt hàng thành công và đã xóa giỏ hàng!", Toast.LENGTH_LONG).show();
                                    Intent intent1 = new Intent(PayOutActivity.this, MainActivity.class);
                                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent1);
                                } else {
                                    Toast.makeText(PayOutActivity.this, "Đơn hàng thành công nhưng KHÔNG xoá được giỏ hàng!", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(PayOutActivity.this, "Lỗi khi đặt hàng!", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Nút quay lại
        binding.button9.setOnClickListener(view -> finish());
    }
}
