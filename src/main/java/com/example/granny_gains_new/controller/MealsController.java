package com.example.granny_gains_new.controller;

import com.example.granny_gains_new.database.RecipeDBHandler;
import com.example.granny_gains_new.model.FavouritedItem;
import com.example.granny_gains_new.model.FavouritesManager;
import com.example.granny_gains_new.model.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
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

        for (Recipe recipe : recipeList) {
            boolean isFavourited = FavouritesManager.getFavourites().stream()
                    .anyMatch(item -> item.getName().equals(recipe.getRecipeName()));
            recipe.setFavourited(isFavourited);
        }

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

                    Image recipeImage;
                    try {
                        recipeImage = new Image(getClass().getResourceAsStream("/com/example/granny_gains_new/meals_images/" + recipe.getPictureUrl() + ".png"));
                    } catch (NullPointerException e) {
                        recipeImage = new Image(getClass().getResourceAsStream("/com/example/granny_gains_new/meals_images/default.png"));
                    }

                    ImageView imageView = new ImageView(recipeImage);
                    imageView.setFitWidth(100);
                    imageView.setPreserveRatio(true);

                    Button favouriteButton = new Button(recipe.isFavourited() ? "Unfavourite" : "Favourite");
                    favouriteButton.setOnAction(event -> handleFavouriteButton(recipe));

                    HBox hBox = new HBox(imageView, favouriteButton);
                    setGraphic(hBox);
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

    private void handleFavouriteButton(Recipe recipe) {
        boolean isFavourite = recipe.isFavourited();
        recipe.setFavourited(!isFavourite);

        if (!isFavourite) {
            FavouritedItem item = new FavouritedItem(recipe.getRecipeName(), "Meal");
            FavouritesManager.addFavouritedItem(item.getName(), item.getCategory());
        } else {
            FavouritesManager.getFavourites().removeIf(favouritedItem -> favouritedItem.getName().equals(recipe.getRecipeName()) && favouritedItem.getCategory().equals("Meal"));
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Favourite Toggle");
        alert.setHeaderText(null);
        alert.setContentText(isFavourite ? "Removed from favourites." : "Added to favourites.");
        alert.showAndWait();

        recipeListView.refresh();
    }

    private void showRecipeDetailPage(Recipe recipe) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/RecipeDetail.fxml"));
        Stage stage = (Stage) recipeListView.getScene().getWindow();
        Scene scene = new Scene(loader.load(), 1000, 800);

        // Use the controller to set recipe data
        RecipeDetailController controller = loader.getController();
        controller.setRecipeData(recipe); // Pass the selected recipe to the detail controller

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/granny_gains_home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        stage.setScene(scene);
    }
}
