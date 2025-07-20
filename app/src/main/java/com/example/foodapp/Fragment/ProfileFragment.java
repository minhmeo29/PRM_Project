package com.example.foodapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodapp.databinding.FragmentProfileBinding;
import com.example.foodapp.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        setUserData();

        // Ban đầu không cho chỉnh sửa
        binding.name.setEnabled(false);
        binding.email.setEnabled(false);
        binding.address.setEnabled(false);
        binding.phone.setEnabled(false);

        // Nút Edit
        binding.editButton.setOnClickListener(v -> {
            boolean editable = !binding.address.isEnabled();
            binding.name.setEnabled(editable);      // bật cả name
            binding.address.setEnabled(editable);
            binding.phone.setEnabled(editable);
        });

        // Nút Save
        binding.button7.setOnClickListener(v -> {
            String newName = binding.name.getText().toString().trim();
            String newAddress = binding.address.getText().toString().trim();
            String newPhone = binding.phone.getText().toString().trim();

            updateUserData(newName, newAddress, newPhone);
        });

        return view;
    }

    private void setUserData() {
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

        if (userId != null) {
            DatabaseReference userRef = database.getReference("users").child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserModel user = snapshot.getValue(UserModel.class);
                    if (user != null) {
                        binding.name.setText(user.getName());
                        binding.email.setText(user.getEmail());
                        binding.address.setText(user.getAddress());
                        binding.phone.setText(user.getPhone());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(requireContext(), "Failed to load profile", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateUserData(String newName, String newAddress, String newPhone) {
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

        if (userId != null) {
            DatabaseReference userRef = database.getReference("users").child(userId);

            userRef.child("name").setValue(newName);
            userRef.child("address").setValue(newAddress);
            userRef.child("phone").setValue(newPhone)
                    .addOnSuccessListener(unused ->
                            Toast.makeText(requireContext(), "Profile updated successfully 😊", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e ->
                            Toast.makeText(requireContext(), "Profile update failed 😒", Toast.LENGTH_SHORT).show());
        }
    }
}
