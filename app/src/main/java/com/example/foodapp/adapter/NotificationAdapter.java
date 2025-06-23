package com.example.foodapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.databinding.NotificationItemBinding;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private ArrayList<String> notification;
    private ArrayList<Integer> notificationImage;

    public NotificationAdapter(ArrayList<String> notification, ArrayList<Integer> notificationImage) {
        this.notification = notification;
        this.notificationImage = notificationImage;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NotificationItemBinding binding = NotificationItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NotificationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return notification.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {
        private NotificationItemBinding binding;

        public NotificationViewHolder(NotificationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            binding.notificationTextView.setText(notification.get(position));
            binding.notificationImageView.setImageResource(notificationImage.get(position));
        }
    }
}

