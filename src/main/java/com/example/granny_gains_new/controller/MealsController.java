package com.example.granny_gains_new.controller;

import com.example.granny_gains_new.database.RecipeDBHandler;
import com.example.granny_gains_new.model.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MealsController {

    @FXML
    private ListView<Recipe> recipeListView;

    @FXML
    private Button HomeButton, AllRecipesButton, BreakfastButton, LunchButton, DinnerButton;

    @FXML
    public void initialize() {
        loadRecipes(null);
    }

    private void loadRecipes(String filter) {
        RecipeDBHandler dbHandler = new RecipeDBHandler();
        List<Recipe> recipeList = dbHandler.getAllRecipes();

        if (filter != null) {
            recipeList = recipeList.stream()
                    .filter(recipe -> recipe.getRecipeType().equalsIgnoreCase(filter))
                    .collect(Collectors.toList());
        }

        ObservableList<Recipe> observableRecipeList = FXCollections.observableArrayList(recipeList);
        recipeListView.setItems(observableRecipeList);

        recipeListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Recipe recipe, boolean empty) {
                super.updateItem(recipe, empty);
                if (empty || recipe == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(recipe.getRecipeName() + " (" + recipe.getRecipeType() + ")\n" + recipe.getDescription());

                    // Load the image using the pictureUrl field from the Recipe model
                    Image recipeImage;
                    try {
                        recipeImage = new Image(getClass().getResourceAsStream("/com/example/granny_gains_new/meals_images/" + recipe.getPictureUrl() + ".png"));
                    } catch (NullPointerException e) {
                        // If the image is missing, use a default image
                        recipeImage = new Image(getClass().getResourceAsStream("/com/example/granny_gains_new/meals_images/default.png"));
                    }

                    ImageView imageView = new ImageView(recipeImage);
                    imageView.setFitWidth(100);
                    imageView.setPreserveRatio(true);

                    setGraphic(imageView);
                }
            }
        });

        recipeListView.setOnMouseClicked(event -> {
            Recipe selectedRecipe = recipeListView.getSelectionModel().getSelectedItem();
            if (selectedRecipe != null) {
                try {
                    showRecipeDetailPage(selectedRecipe);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showRecipeDetailPage(Recipe recipe) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/RecipeDetail.fxml"));
        Stage stage = (Stage) recipeListView.getScene().getWindow();
        Scene scene = new Scene(loader.load(), 1000, 800);

        RecipeDetailController controller = loader.getController();
        controller.setRecipeData(recipe);

        stage.setScene(scene);
    }

    @FXML
    protected void showAllRecipes() {
        loadRecipes(null);
    }

    @FXML
    protected void showBreakfastRecipes() {
        loadRecipes("breakfast");
    }

    @FXML
    protected void showLunchRecipes() {
        loadRecipes("lunch");
    }

    @FXML
    protected void showDinnerRecipes() {
        loadRecipes("dinner");
    }

    @FXML
    protected void handleBackToHome() throws IOException {
        Stage stage = (Stage) HomeButton.getScene().getWindow();
        double width = stage.getWidth();
        double height = stage.getHeight();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/granny_gains_home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setScene(scene);
    }
}
