package com.example.foodapp.model;

public class CartItems {
    private String foodName;
    private String foodPrice;
    private String foodDescription;
    private String foodImage;
    private Integer foodQuantity;
    private String foodngredients;

    public CartItems() {
    }

    public CartItems(String foodName, String foodPrice, String foodDescription, String foodImage, Integer foodQuantity, String foodngredients) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodDescription = foodDescription;
        this.foodImage = foodImage;
        this.foodQuantity = foodQuantity;
        this.foodngredients = foodngredients;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public Integer getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(Integer foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public String getFoodngredients() {
        return foodngredients;
    }

    public void setFoodngredients(String foodngredients) {
        this.foodngredients = foodngredients;
    }
} 