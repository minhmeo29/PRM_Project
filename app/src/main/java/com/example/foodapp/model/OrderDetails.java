package com.example.foodapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderDetails implements Serializable {

    private String userUid;
    private String userName;
    private ArrayList<String> foodNames;
    private ArrayList<String> foodImages;
    private ArrayList<String> foodPrices;
    private ArrayList<Integer> foodQuantities;
    private String address;
    private String totalPrice;
    private String phoneNumber;
    private boolean orderAccepted;
    private boolean paymentReceived;
    private String itemPushKey;
    private long currentTime;
    private boolean AcceptedOrder;

    public OrderDetails() {
        // Required for Firebase
    }

    public OrderDetails(String userId, String name, ArrayList<String> foodItemName,
                        ArrayList<String> foodItemPrice, ArrayList<String> foodItemImage,
                        ArrayList<Integer> foodItemQuantities, String address,
                        String totalAmount, String phone, long time, String itemPushKey,
                        boolean orderAccepted, boolean paymentReceived) {

        this.userUid = userId;
        this.userName = name;
        this.foodNames = foodItemName;
        this.foodPrices = foodItemPrice;
        this.foodImages = foodItemImage;
        this.foodQuantities = foodItemQuantities;
        this.address = address;
        this.totalPrice = totalAmount;
        this.phoneNumber = phone;
        this.currentTime = time;
        this.itemPushKey = itemPushKey;
        this.orderAccepted = orderAccepted;
        this.paymentReceived = paymentReceived;
    }

    // Getters and Setters
    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<String> getFoodNames() {
        return foodNames;
    }

    public void setFoodNames(ArrayList<String> foodNames) {
        this.foodNames = foodNames;
    }

    public ArrayList<String> getFoodImages() {
        return foodImages;
    }

    public void setFoodImages(ArrayList<String> foodImages) {
        this.foodImages = foodImages;
    }

    public ArrayList<String> getFoodPrices() {
        return foodPrices;
    }

    public void setFoodPrices(ArrayList<String> foodPrices) {
        this.foodPrices = foodPrices;
    }

    public ArrayList<Integer> getFoodQuantities() {
        return foodQuantities;
    }

    public void setFoodQuantities(ArrayList<Integer> foodQuantities) {
        this.foodQuantities = foodQuantities;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isOrderAccepted() {
        return orderAccepted;
    }

    public void setOrderAccepted(boolean orderAccepted) {
        this.orderAccepted = orderAccepted;
    }

    public boolean isPaymentReceived() {
        return paymentReceived;
    }

    public void setPaymentReceived(boolean paymentReceived) {
        this.paymentReceived = paymentReceived;
    }

    public String getItemPushKey() {
        return itemPushKey;
    }

    public void setItemPushKey(String itemPushKey) {
        this.itemPushKey = itemPushKey;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public boolean isAcceptedOrder() {
        return AcceptedOrder;
    }
    public void setAcceptedOrder(boolean acceptedOrder) {
        AcceptedOrder = acceptedOrder;
    }
}