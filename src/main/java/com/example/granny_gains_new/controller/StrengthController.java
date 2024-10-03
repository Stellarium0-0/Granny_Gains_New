package com.example.granny_gains_new.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.Parent;

public class StrengthController {
    @FXML
    private Button HomeButton;

    @FXML
    private Button CardioButton;

    @FXML
    private Button StrengthButton;

    @FXML
    private Button HIITButton;

    @FXML
    private Button HelpButton;

    @FXML
    protected void handleHelp() throws IOException {
        Stage stage = (Stage) HelpButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/fitness_help.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setScene(scene);
    }


    @FXML
    protected void NavCardio() throws IOException {
        Stage stage = (Stage) CardioButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/FitnessCardio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setScene(scene);
    }

    @FXML
    protected void NavStrength() throws IOException {
        Stage stage = (Stage) StrengthButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/FitnessStrength.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setScene(scene);
    }

    @FXML
    protected void NavHIIT() throws IOException {
        Stage stage = (Stage) HIITButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/FitnessHIIT.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setScene(scene);
    }

    @FXML
    protected void handleBackToHome() throws IOException {
        Stage stage = (Stage) HomeButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/granny_gains_home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        stage.setScene(scene);
    }


    @FXML
    private ImageView Strength1, Strength2, Strength3, Strength4;

    @FXML
    private Label Strength1Title, Strength2Title, Strength3Title, Strength4Title;

    // Method to initialize the controller
    @FXML
    public void initialize() {
        loadCardioWorkouts();
    }

    // Method to load cardio workouts from CSV and update the UI
    private void loadCardioWorkouts() {
        String csvFile = "src/main/java/com/example/granny_gains_new/database/strength.csv";
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int counter = 1;
            while ((line = br.readLine()) != null) {
                // Skip the header row
                if (counter == 1) {
                    counter++;
                    continue;
                }

                // Split the line into columns
                String[] workoutData = line.split(csvSplitBy);

                String title = workoutData[0].replace("\"", "").trim();
                String thumbnailPath = workoutData[1].replace("\"", "").trim();
                String videoLink = workoutData[2].replace("\"", "").trim();

                // Assign the title, thumbnail, and video link to the correct ImageView and Label based on the counter
                switch (counter) {
                    case 2:
                        updateWorkoutTile(Strength1, Strength1Title, title, thumbnailPath, videoLink);
                        break;
                    case 3:
                        updateWorkoutTile(Strength2, Strength2Title, title, thumbnailPath, videoLink);
                        break;
                    case 4:
                        updateWorkoutTile(Strength3, Strength3Title, title, thumbnailPath, videoLink);
                        break;
                    case 5:
                        updateWorkoutTile(Strength4, Strength4Title, title, thumbnailPath, videoLink);
                        break;
                }

                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to update the ImageView and Label for a workout tile in FitnessCardio.fxml
    private void updateWorkoutTile(ImageView imageView, Label titleLabel, String title, String imagePath, String videoLink) {
        // Load and set the image in the ImageView
        try {
            Image thumbnail = new Image(getClass().getResource(imagePath).toExternalForm());
            imageView.setImage(thumbnail);
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

        // Set the title in the Label
        titleLabel.setText(title);

        // Add event handler to open video on click
        imageView.setOnMouseClicked(event -> openVideoPlayer(videoLink));
        titleLabel.setOnMouseClicked(event -> openVideoPlayer(videoLink));  // You can also click the title
    }

    // Method to open a new scene with the video player
    private void openVideoPlayer(String videoUrl) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/fitness_player.fxml"));
            Parent root = loader.load();

            // Get the controller for the new scene
            FitnessVideoPlayerController controller = loader.getController();
            controller.setVideoUrl(videoUrl);


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Video Player");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}