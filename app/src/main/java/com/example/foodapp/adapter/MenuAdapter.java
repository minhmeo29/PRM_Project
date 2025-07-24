package com.example.foodapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.NumberFormat;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.DetailActivity;
import com.example.foodapp.R;
import com.example.foodapp.model.MenuItem;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private final List<MenuItem> menuItems;
    private final Context context;

    public MenuAdapter(List<MenuItem> menuItems, Context context) {
        this.menuItems = menuItems;
        this.context = context;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView menuFoodName, menuPrice;
        ImageView menuImage;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            menuFoodName = itemView.findViewById(R.id.menuFoodName);
            menuPrice = itemView.findViewById(R.id.menuPrice);
            menuImage = itemView.findViewById(R.id.menuImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        openDetailsActivity(position);
                    }
                }
            });
        }

        private void openDetailsActivity(int position) {
            MenuItem menuItem = menuItems.get(position);
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("MenuItemName", menuItem.getFoodName());
            intent.putExtra("MenuItemImage", String.valueOf(menuItem.getFoodImage())); // Đảm bảo là String
            intent.putExtra("MenuItemDescription", menuItem.getFoodDescription());
            intent.putExtra("MenuItemIngredients", menuItem.getFoodIngredient());
            intent.putExtra("MenuItemPrice", String.valueOf(menuItem.getFoodPrice())); // Đảm bảo là String
            context.startActivity(intent);
        }

        public void bind(int position) {
            MenuItem menuItem = menuItems.get(position);
            menuFoodName.setText(menuItem.getFoodName());

            String price = menuItem.getFoodPrice();
            if (!price.contains("VND")) {
                // Thêm dấu phẩy ngăn cách hàng nghìn nếu cần
                try {
                    int priceValue = Integer.parseInt(price);
                    price = String.format("%,d VND", priceValue);
                } catch (NumberFormatException e) {
                    price = price + " VND"; // fallback nếu không thể parse
                }
            }

            menuPrice.setText(price);

            if (menuItem.getFoodImage() != null) {
                Glide.with(context).load(Uri.parse(menuItem.getFoodImage())).into(menuImage);
            }
        }


    }
}