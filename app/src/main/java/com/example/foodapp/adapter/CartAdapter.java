package com.example.foodapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.databinding.CartItemBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private final Context context;
    private final List<String> cartItems;
    private final List<String> cartItemPrices;
    private final List<String> cartDescriptions;
    private final List<String> cartImages;
    private final List<Integer> cartQuantity;
    private final List<String> cartIngredient;
    private final FirebaseAuth auth;
    private final DatabaseReference cartItemsReference;
    private int[] itemQuantities;

    public CartAdapter(Context context,
                       List<String> cartItems,
                       List<String> cartItemPrices,
                       List<String> cartDescriptions,
                       List<String> cartImages,
                       List<Integer> cartQuantity,
                       List<String> cartIngredient) {
        this.context = context;
        this.cartItems = cartItems;
        this.cartItemPrices = cartItemPrices;
        this.cartDescriptions = cartDescriptions;
        this.cartImages = cartImages;
        this.cartQuantity = cartQuantity;
        this.cartIngredient = cartIngredient;
        this.auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "";
        this.cartItemsReference = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("CartItems");
        this.itemQuantities = new int[cartItems.size()];
        for (int i = 0; i < cartItems.size(); i++) {
            itemQuantities[i] = 1;
        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartItemBinding binding = CartItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public List<Integer> getUpdatedItemsQuantities() {
        return cartQuantity;
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        private final CartItemBinding binding;

        public CartViewHolder(CartItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            int quantity = itemQuantities[position];
            binding.cartFoodName.setText(cartItems.get(position));
            binding.cartitemPrice.setText(cartItemPrices.get(position));
            // Xử lý ảnh
            String uriString = cartImages.get(position);
            Uri uri = null;
            if (uriString == null || uriString.equals("null")) {
                binding.cartimage.setImageResource(com.example.foodapp.R.drawable.menu1); // Ảnh mặc định
            } else {
                try {
                    // Nếu là số nguyên (resource id), chuyển thành resource uri
                    int resId = Integer.parseInt(uriString);
                    uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + resId);
                } catch (NumberFormatException e) {
                    uri = Uri.parse(uriString);
                }
                Glide.with(context).load(uri).into(binding.cartimage);
            }
            binding.cartitemQuantity.setText(String.valueOf(quantity));
            // Xử lý mô tả, nguyên liệu nếu muốn hiển thị (nếu có TextView)
            // binding.cartitemDescription.setText(cartDescriptions.get(position) != null ? cartDescriptions.get(position) : "Không có thông tin");
            // binding.cartitemIngredient.setText(cartIngredient.get(position) != null ? cartIngredient.get(position) : "Không có thông tin");
            binding.minusbutton.setOnClickListener(v -> deceaseQuantity(position));
            binding.plusbutton.setOnClickListener(v -> increaseQuantity(position));
            binding.deleteButton.setOnClickListener(v -> {
                int itemPosition = getAdapterPosition();
                if (itemPosition != RecyclerView.NO_POSITION) {
                    deleteItem(itemPosition);
                }
            });
        }

        private void increaseQuantity(int position) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++;
                cartQuantity.set(position, itemQuantities[position]);
                binding.cartitemQuantity.setText(String.valueOf(itemQuantities[position]));
            }
        }

        private void deceaseQuantity(int position) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--;
                cartQuantity.set(position, itemQuantities[position]);
                binding.cartitemQuantity.setText(String.valueOf(itemQuantities[position]));
            }
        }

        private void deleteItem(int position) {
            getUniqueKeyAtPosition(position, uniqueKey -> {
                if (uniqueKey != null) {
                    removeItem(position, uniqueKey);
                }
            });
        }

        private void removeItem(int position, String uniqueKey) {
            cartItemsReference.child(uniqueKey).removeValue().addOnSuccessListener(unused -> {
                cartItems.remove(position);
                cartImages.remove(position);
                cartDescriptions.remove(position);
                cartQuantity.remove(position);
                cartItemPrices.remove(position);
                cartIngredient.remove(position);
                Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                // update itemQuantities
                int[] newQuantities = new int[itemQuantities.length - 1];
                for (int i = 0, j = 0; i < itemQuantities.length; i++) {
                    if (i != position) {
                        newQuantities[j++] = itemQuantities[i];
                    }
                }
                itemQuantities = newQuantities;
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItems.size());
            }).addOnFailureListener(e -> Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show());
        }

        private void getUniqueKeyAtPosition(int positionRetrieve, OnCompleteListener onComplete) {
            cartItemsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String uniqueKey = null;
                    int index = 0;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (index == positionRetrieve) {
                            uniqueKey = dataSnapshot.getKey();
                            break;
                        }
                        index++;
                    }
                    onComplete.onComplete(uniqueKey);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error if needed
                }
            });
        }
    }

    interface OnCompleteListener {
        void onComplete(String uniqueKey);
    }
}