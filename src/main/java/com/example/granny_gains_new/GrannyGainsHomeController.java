package com.example.granny_gains_new;
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
    private Button logOutButton;

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Meals_Page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleBackToSignIn() {


        try {
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sign_in_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 650);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAbout() {
        // Handle About click if necessary
        System.out.println("About clicked!");
    }

    @FXML
    private void handleServices() {
        // Handle Services click if necessary
        System.out.println("Services clicked!");
    }

    @FXML
    private void handlePackages() {
        // Handle Packages click if necessary
        System.out.println("Packages clicked!");
    }

    @FXML
    private void handleContact() {
        // Handle Contact click if necessary
        System.out.println("Contact clicked!");
    }

    @FXML
    private void handleFitnessLog() {
        // Handle Fitness Log click if necessary
        System.out.println("Fitness Log clicked!");
    }

    @FXML
    private void handleSettings() {
        // Handle Settings click if necessary
        System.out.println("Settings clicked!");
    }
}
