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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PayOutActivity extends AppCompatActivity {

    ActivityPayOutBinding binding;
    ArrayList<String> foodNames, foodPrices, foodImages;
    ArrayList<Integer> foodQuantities;
    String userUid, totalPrice;
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
            String address = binding.address.getText().toString().trim();
            String phone = binding.phone.getText().toString().trim();

            if (address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ Địa chỉ và Số điện thoại!", Toast.LENGTH_SHORT).show();
                return;
            }

            String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;
            if (userId == null) {
                Toast.makeText(this, "Không xác định được người dùng!", Toast.LENGTH_SHORT).show();
                return;
            }
            DatabaseReference userRef = database.getReference("users").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    UserModel user = snapshot.getValue(UserModel.class);
                    String name = (user != null && user.getName() != null && !user.getName().isEmpty()) ? user.getName() : binding.name.getText().toString().trim();
                    if (name.isEmpty()) {
                        Toast.makeText(PayOutActivity.this, "Vui lòng nhập tên!", Toast.LENGTH_SHORT).show();
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
                            // ✅ Dùng userId từ FirebaseAuth, KHÔNG dùng userUid từ intent
                            database.getReference().child("users").child(userId).child("CartItems").removeValue()
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
                            Toast.makeText(PayOutActivity.this, "Lỗi đặt hàng!", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(PayOutActivity.this, "Không lấy được thông tin user!", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Nút quay lại
        binding.button9.setOnClickListener(view -> finish());
    }
}
