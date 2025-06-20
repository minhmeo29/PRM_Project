package com.example.foodapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.databinding.ActivityChooseLocationBinding;

public class ChooseLocationActivity extends AppCompatActivity {

    private ActivityChooseLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Binding view
        binding = ActivityChooseLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Danh sách địa điểm
        String[] locationList = {
                "Hà Nội",
                "Hồ Chí Minh",
                "Đà Nẵng",
                "Cần Thơ",
                "Nha Trang",
                "Hải Phòng",
                "Quảng Ninh"
        };

        // Tạo adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                locationList
        );

        // Gán adapter vào AutoCompleteTextView
        AutoCompleteTextView autoCompleteTextView = binding.listLocationDropdown;
        autoCompleteTextView.setAdapter(adapter);
    }
}
