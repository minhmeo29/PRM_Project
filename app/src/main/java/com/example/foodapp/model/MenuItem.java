package com.example.foodapp.model;

public class MenuItem {
    private String foodName;
    private String foodPrice;
    private String foodDescription;
    private String foodImage;
    private String foodIngredient;

    public MenuItem() {
    }

    public MenuItem(String foodName, String foodPrice, String foodDescription, String foodImage, String foodIngredient) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodDescription = foodDescription;
        this.foodImage = foodImage;
        this.foodIngredient = foodIngredient;
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

    public String getFoodIngredient() {
        return foodIngredient;
    }

    public void setFoodIngredient(String foodIngredient) {
        this.foodIngredient = foodIngredient;
    }
} 