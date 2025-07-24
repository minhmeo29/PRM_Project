package com.example.foodapp; // đổi thành package của bạn

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.example.foodapp.LoginActivity; // đổi thành class login thực tế của bạn

public class LogoutHelper {

    public static void logoutUser(Context context) {
        // Đăng xuất Firebase
        FirebaseAuth.getInstance().signOut();

        // Hiển thị thông báo
        Toast.makeText(context, "Bạn đã đăng xuất", Toast.LENGTH_SHORT).show();

        // Chuyển về màn hình Login
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        context.startActivity(intent);

        // Nếu context là Activity thì kết thúc luôn
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }
}
