package com.example.foodapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.text.NumberFormat;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.databinding.BuyAgainItemBinding;

import java.util.List;

public class BuyAgainAdapter extends RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder> {

    private final List<String> buyAgainFoodName;
    private final List<String> buyAgainFoodPrice;
    private final List<String> buyAgainFoodImage;
    private final Context context;

    public BuyAgainAdapter(List<String> buyAgainFoodName,
                           List<String> buyAgainFoodPrice,
                           List<String> buyAgainFoodImage,
                           Context context) {
        this.buyAgainFoodName = buyAgainFoodName;
        this.buyAgainFoodPrice = buyAgainFoodPrice;
        this.buyAgainFoodImage = buyAgainFoodImage;
        this.context = context;
    }

    @NonNull
    @Override
    public BuyAgainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BuyAgainItemBinding binding = BuyAgainItemBinding.inflate(inflater, parent, false);
        return new BuyAgainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyAgainViewHolder holder, int position) {
        // Bảo vệ khỏi lỗi IndexOutOfBounds
        if (position < buyAgainFoodName.size() &&
                position < buyAgainFoodPrice.size() &&
                position < buyAgainFoodImage.size()) {
            holder.bind(
                    buyAgainFoodName.get(position),
                    buyAgainFoodPrice.get(position),
                    buyAgainFoodImage.get(position)
            );
        }
    }

    @Override
    public int getItemCount() {
        return Math.min(buyAgainFoodName.size(),
                Math.min(buyAgainFoodPrice.size(), buyAgainFoodImage.size()));
    }

    class BuyAgainViewHolder extends RecyclerView.ViewHolder {
        private final BuyAgainItemBinding binding;

        public BuyAgainViewHolder(BuyAgainItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String foodName, String foodPrice, String foodImage) {
            binding.buyAgainFoodName.setText(foodName);

            try {
                // Nếu chuỗi đã chứa "VND" thì hiển thị luôn
                if (foodPrice.contains("VND")) {
                    binding.buyAgainFoodPrice.setText(foodPrice);
                } else {
                    int price = Integer.parseInt(foodPrice);
                    NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
                    String formattedPrice = formatter.format(price) + " VND";
                    binding.buyAgainFoodPrice.setText(formattedPrice);
                }
            } catch (NumberFormatException e) {
                // fallback: giữ nguyên giá trị nếu không thể parse
                binding.buyAgainFoodPrice.setText(foodPrice);
            }

            if (foodImage != null && !foodImage.isEmpty()) {
                Uri uri = Uri.parse(foodImage);
                Glide.with(context)
                        .load(uri)
                        .into(binding.buyAgainFoodImage);
            } else {
                binding.buyAgainFoodImage.setImageResource(android.R.drawable.ic_menu_report_image); // ảnh mặc định
            }
        }

    }
}
