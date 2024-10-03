package com.example.granny_gains_new.model;

import java.util.List;

public class Recipe {
    private int recipeId;
    private String recipeType;
    private String recipeName;
    private int servings;
    private int calories;
    private String description;
    private List<String> ingredients; // List of ingredients
    private List<String> recipeMethod; // List of steps in the recipe
    private String pictureUrl;
    private boolean isFavourited; // Flag indicating if it's a favorite recipe

    // Constructor for retrieving an existing recipe (with ID)
    public Recipe(int recipeId, String recipeType, String recipeName, int servings, int calories,
                  String description, List<String> ingredients, List<String> recipeMethod, String pictureUrl) {
        this.recipeId = recipeId;
        this.recipeType = recipeType;
        this.recipeName = recipeName;
        this.servings = servings;
        this.calories = calories;
        this.description = description;
        this.ingredients = ingredients;
        this.recipeMethod = recipeMethod;
        this.pictureUrl = pictureUrl;
        this.isFavourited = isFavourited; // Initialize this field
    }

    public Recipe(int recipeId, String recipeName) {

    }

    // Getters and setters for each field
    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(String recipeType) {
        this.recipeType = recipeType;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getRecipeMethod() {
        return recipeMethod;
    }

    public void setRecipeMethod(List<String> recipeMethod) {
        this.recipeMethod = recipeMethod;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public boolean isFavourited() { // Getter method
        return isFavourited;
    }

    public void setFavourited(boolean favourited) { // Setter method
        isFavourited = favourited;
    }
}
