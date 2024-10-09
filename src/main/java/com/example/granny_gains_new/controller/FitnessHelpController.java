package com.example.granny_gains_new.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller class for handling the Fitness Help section in the Granny Gains application.
 * <p>
 * This class provides functionality to navigate back to the Cardio view from the Help page.
 * </p>
 */
public class FitnessHelpController {

    @FXML
    private Button HomeButton;

    /**
     * Handles navigation back to the Cardio view when the Home button is clicked.
     *
     * @throws IOException if the FXML file for the Cardio view is not found.
     */
    @FXML
    protected void handleBackToHome() throws IOException {
        Stage stage = (Stage) HomeButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/FitnessCardio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setScene(scene);
    }
}

