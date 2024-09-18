package com.example.granny_gains_new.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
public class GrannyGainsHomeController {
    @FXML
    private ImageView logoImage;

    @FXML
    private Label aboutLabel;

    @FXML
    private Label servicesLabel;

    @FXML
    private Label packagesLabel;

    @FXML
    private Label contactLabel;

    @FXML
    private Button viewServicesButton;

    @FXML
    private Button fitnessButton;

    @FXML
    private Button SettingsButton;

    @FXML
    private Button FriendsButton;

    @FXML
    private Button mealsButton;

    // Initialize method to set up initial states
    @FXML
    public void initialize() {
        // Load the main image
        Image logo = new Image(getClass().getResource("/com/example/granny_gains_new/images/landing.jpg").toExternalForm());
        logoImage.setImage(logo);
    }

    @FXML
    private void handleViewServices() {

        System.out.println("View Services clicked!");

    }

    @FXML
    public void handleMeals() {

        try {
            Stage stage = (Stage) mealsButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/Meals_Page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleFitnessLog() {

        System.out.println("Fitness Log clicked!");
    }

    @FXML
    private void handleSettings() {
        try {
            Stage stage = (Stage) mealsButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/settings_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleFriends() {
        try {
            Stage stage = (Stage) FriendsButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/friends_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
