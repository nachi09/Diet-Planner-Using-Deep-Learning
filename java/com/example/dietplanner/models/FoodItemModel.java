package com.example.dietplanner.models;

public class FoodItemModel {
    String name;
    String calories;

    String quantity;

    public FoodItemModel (String name, String calories, String quantity) {
        this.name = name;
        this.calories = calories;
        this.quantity = quantity;
    }

    public String getName () {
        return name;
    }

    public String getCalories () {
        return calories;
    }

    public String getQuantity () {
        return quantity;
    }
}
