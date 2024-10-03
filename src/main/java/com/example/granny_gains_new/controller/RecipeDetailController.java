package com.example.granny_gains_new.controller;

import com.example.granny_gains_new.model.FavouritedItem;
import com.example.granny_gains_new.model.Recipe;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class RecipeDetailController {

    @FXML
    private Label recipeNameLabel;

    @FXML
    private ImageView recipeImageView;

    @FXML
    private TextArea recipeDescription;

    public void setRecipeData(Recipe recipe) {
        recipeNameLabel.setText(recipe.getRecipeName());

        // Load the image associated with the recipe (make sure the image URL is valid)
        Image recipeImage = new Image(getClass().getResourceAsStream("/com/example/granny_gains_new/meals_images/" + recipe.getPictureUrl() + ".png"));
        recipeImageView.setImage(recipeImage);

        recipeDescription.setText(recipe.getDescription());
    }

    @FXML
    protected void handleBackToMeals() throws IOException {
        Stage stage = (Stage) recipeNameLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/meals_page.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 800);
        stage.setScene(scene);
    }

    public void setRecipeDetails(FavouritedItem selectedItem) {

    }
}

