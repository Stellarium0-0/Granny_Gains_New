package com.example.granny_gains_new.model;

import java.io.Serializable;

public class FavouritedItem implements Serializable {
    private static final long serialVersionUID = 1L; // Added for serialization
    private String name;  // Name of the meal or fitness item
    private String category;  // Category of the item (e.g., "Fitness" or "Meal")
    private String productLink; // Link to the actual product page

    // Constructor
    public FavouritedItem(String name, String category) {
        this.name = name;
        this.category = category;
        this.productLink = productLink; // Initialize product link
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;  // Getter for category
    }

    public void setCategory(String category) {
        this.category = category;  // Setter for category
    }

    public String getProductLink() {
        return productLink; // Getter for product link
    }

    public void setProductLink(String productLink) {
        this.productLink = productLink; // Setter for product link
    }

    @Override
    public String toString() {
        return name;  // Return the name of the meal
    }
}
