package com.example.granny_gains_new.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.granny_gains_new.database.DatabaseConnection;

import java.io.IOException;

public class GrannyGainsHomeController {

    @FXML
    private ImageView logoImage;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button mealsButton, fitnessButton, settingsButton, FriendsButton;


    // Initialize method to set up the page
    @FXML
    private Button logOutButton;

    // Initialize method to set up initial states

    @FXML
    public void initialize() {
        loadLogoImage();
        loadUserNameFromSession();
    }

    // Load the main logo image
    private void loadLogoImage() {
        try {
            Image logo = new Image(getClass().getResource("/com/example/granny_gains_new/images/landing.jpg").toExternalForm());
            logoImage.setImage(logo);
        } catch (NullPointerException e) {
            System.err.println("Error loading logo image: " + e.getMessage());
        }
    }

    // Load the user's name from the latest session in the database
    private void loadUserNameFromSession() {
        String userName = fetchUserNameFromSession();
        if (userName != null && !userName.isEmpty()) {
            welcomeLabel.setText("Welcome, " + userName + "!");
        } else {
            welcomeLabel.setText("Welcome, Guest!");
        }
    }

    // Fetch user's name using the latest active session
    private String fetchUserNameFromSession() {
        String userName = "";
        String query = "SELECT u.first_name, u.last_name FROM User u "
                + "JOIN sessions s ON u.email = s.user_id "
                + "ORDER BY s.login_time DESC LIMIT 1"; // Fetch the latest session

        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userName = rs.getString("first_name") + " " + rs.getString("last_name");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user name from session: " + e.getMessage());
        }
        return userName;
    }

    @FXML
    public void handleMeals() {
        navigateToPage("/com/example/granny_gains_new/Meals_Page.fxml", "Meals Page");
    }

    @FXML
    public void handleFitness() {
        navigateToPage("/com/example/granny_gains_new/FitnessCardio.fxml", "Fitness Page");
    }

    @FXML
    public void handleSettings() {
        navigateToPage("/com/example/granny_gains_new/settings_page.fxml", "Settings Page");
    }

    @FXML
    public void handleFriends() {
        navigateToPage("/com/example/granny_gains_new/Friends_page.fxml", "Friends Page");
    }

    // Generic method to navigate between pages
    private void navigateToPage(String fxmlFilePath, String pageName) {
        try {
            Stage stage = (Stage) mealsButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
            stage.setScene(scene);
            System.out.println("Navigated to " + pageName);
        } catch (IOException e) {
            System.err.println("Error loading " + pageName + ": " + e.getMessage());
        }
    }

    @FXML
    protected void handleBackToSignIn() {
        try {
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/sign_in_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 650);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button favouritesButton; // Define the button that will open the favourites page

    @FXML
    private void handleFavouritesButtonClick() {
        try {
            VBox favouritesPage = FXMLLoader.load(getClass().getResource("/com/example/granny_gains_new/Favourites_Page.fxml"));
            Scene scene = new Scene(favouritesPage);

            Stage stage = (Stage) favouritesButton.getScene().getWindow(); // Use favouritesButton
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
