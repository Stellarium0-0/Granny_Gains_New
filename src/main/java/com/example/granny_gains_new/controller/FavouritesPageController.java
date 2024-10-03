package com.example.granny_gains_new.controller;

import com.example.granny_gains_new.model.FavouritesManager;
import com.example.granny_gains_new.model.FavouritedItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class FavouritesPageController {

    @FXML
    private ListView<FavouritedItem> fitnessListView;

    @FXML
    private ListView<FavouritedItem> mealsListView;

    @FXML
    private Button backButton;

    @FXML
    public void initialize() {
        loadFavourites();
    }

    private void loadFavourites() {
        ObservableList<FavouritedItem> favourites = FXCollections.observableArrayList(FavouritesManager.getFavourites());
        ObservableList<FavouritedItem> fitnessFavourites = FXCollections.observableArrayList();
        ObservableList<FavouritedItem> mealFavourites = FXCollections.observableArrayList();

        for (FavouritedItem item : favourites) {
            if ("Fitness".equalsIgnoreCase(item.getCategory())) {
                fitnessFavourites.add(item);
            } else if ("Meal".equalsIgnoreCase(item.getCategory())) {
                mealFavourites.add(item);
            }
        }

        fitnessListView.setItems(fitnessFavourites);
        mealsListView.setItems(mealFavourites);

        addContextMenu(fitnessListView);
        addContextMenu(mealsListView);

        addItemClickEvent(fitnessListView);
        addItemClickEvent(mealsListView);
    }

    private void addItemClickEvent(ListView<FavouritedItem> listView) {
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !event.isConsumed()) {
                FavouritedItem selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    if ("Meal".equalsIgnoreCase(selectedItem.getCategory())) {
                        // Redirect to RecipeDetail.fxml
                        loadRecipeDetailPage(selectedItem);
                    }
                }
                event.consume();
            }
        });
    }

    private void loadRecipeDetailPage(FavouritedItem selectedItem) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/RecipeDetail.fxml"));
            Stage stage = (Stage) mealsListView.getScene().getWindow(); // or any other node from the current scene
            Scene scene = new Scene(loader.load());

            // Pass the selected item's data to the RecipeDetailController
            RecipeDetailController controller = loader.getController();
            controller.setRecipeDetails(selectedItem);

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (you can show an alert to the user if necessary)
        }
    }

    private void addContextMenu(ListView<FavouritedItem> listView) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem unfavoriteItem = new MenuItem("Unfavorite");

        unfavoriteItem.setOnAction(e -> {
            FavouritedItem selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                FavouritesManager.removeFavouritedItem(selectedItem.getName(), selectedItem.getCategory());
                loadFavourites();
            }
        });

        listView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(listView, event.getScreenX(), event.getScreenY());
            }
        });

        contextMenu.getItems().add(unfavoriteItem);
        listView.setContextMenu(contextMenu);
    }

    @FXML
    protected void handleBackToHome() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/granny_gains_home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        stage.setScene(scene);
    }
}
