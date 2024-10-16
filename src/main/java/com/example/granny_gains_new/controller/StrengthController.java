package com.example.granny_gains_new.controller;

import com.example.granny_gains_new.database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

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
    private Button LogButton;

    @FXML
    private Button logStrength1, logStrength2, logStrength3, logStrength4;

    @FXML
    private ImageView unfavourited1, unfavourited2, unfavourited3, unfavourited4; // ImageViews for the heart icons

    private boolean[] isFavourited = {false, false, false, false}; // Track if each workout is favorited

    private String[] currentWorkoutNames = {
            "20 Min Strength Training for Seniors",
            "30 minute Full Body Strength Workout",
            "The Best Tips for Strength Training",
            "30 Min Strength Training for Over 60"
    };

    @FXML
    public void toggleFavorite(MouseEvent event) {
        // Get the source of the click event
        ImageView source = (ImageView) event.getSource();
        int index = -1;

        if (source == unfavourited1) {
            index = 0;
        } else if (source == unfavourited2) {
            index = 1;
        } else if (source == unfavourited3) {
            index = 2;
        } else if (source == unfavourited4) {
            index = 3;
        }

        if (index == -1) {
            System.out.println("Unknown heart icon clicked.");
            return;
        } else {
            System.out.println("Heart icon " + index + " clicked. Current favorited status: " + isFavourited[index]);
        }


        if (isFavourited[index]) {
            // Remove from favorites
            boolean success = removeWorkoutFromFitnessTable(currentWorkoutNames[index]);
            if (success) {
                updateHeartImage(source, false);
                System.out.println("Workout Unfavorited");
            } else {
                System.out.println("Failed to unfavorite the workout.");
            }
        } else {
            // Save to favorites
            boolean success = addWorkoutToFitnessTable(currentWorkoutNames[index]);
            if (success) {
                updateHeartImage(source, true);
                System.out.println("Workout Favourited");
            } else {
                System.out.println("Failed to favorite the workout due to database errors.");
            }
        }

        isFavourited[index] = !isFavourited[index]; // Toggle favorited status
    }

    private void updateHeartImage(ImageView heartIcon, boolean favorited) {
        String heartImagePath = favorited
                ? "/com/example/granny_gains_new/images/icons8-favorite-50 (1).png" // Filled heart image
                : "/com/example/granny_gains_new/images/icons8-favorite-50.png"; // Unfilled heart image
        Image heartImage = new Image(getClass().getResourceAsStream(heartImagePath));
        heartIcon.setImage(heartImage);
    }

    private void updateHeartIcons() {
        // Set initial state of heart icons based on favorited status
        for (int i = 0; i < isFavourited.length; i++) {
            ImageView heartIcon = null;
            switch (i) {
                case 0:
                    heartIcon = unfavourited1;
                    break;
                case 1:
                    heartIcon = unfavourited2;
                    break;
                case 2:
                    heartIcon = unfavourited3;
                    break;
                case 3:
                    heartIcon = unfavourited4;
                    break;
            }
            if (heartIcon != null) {
                updateHeartImage(heartIcon, isFavourited[i]);
            }
        }
    }

    public boolean addWorkoutToFitnessTable(String workoutName) {
        String insertSQL = "INSERT INTO FitnessTable (workout_name, saved_date) VALUES (?, ?)";
        String savedDate = LocalDate.now().toString();  // Get the current date

        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, workoutName);
            preparedStatement.setString(2, savedDate);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Workout added to favorites: " + workoutName + " on " + savedDate);
                return true; // Success
            } else {
                System.out.println("No rows affected when adding workout.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Failure
    }

    public boolean removeWorkoutFromFitnessTable(String workoutName) {
        String deleteSQL = "DELETE FROM FitnessTable WHERE workout_name = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            preparedStatement.setString(1, workoutName);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Workout removed from favorites: " + workoutName);
                return true; // Success
            } else {
                System.out.println("No rows affected when removing workout.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Failure
    }

    @FXML
    private void handleLog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/fitnesslog.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading the favourites page.");
        }
    }

    @FXML
    private void markAsComplete(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String workoutName = ""; // This will hold the name of the workout to save to the database

        if (clickedButton == logStrength1) {
            workoutName = Strength1Title.getText();
        } else if (clickedButton == logStrength2) {
            workoutName = logStrength2.getText();
        } else if (clickedButton == logStrength3) {
            workoutName = Strength3Title.getText();
        } else if (clickedButton == logStrength4) {
            workoutName = Strength4Title.getText();
        }

        // Update button text
        clickedButton.setText("Completed");

        // Call method to update the database
        addWorkoutToDiary(workoutName);
    }

    // Method to add workout to diary database
    private void addWorkoutToDiary(String workoutName) {
        String query = "INSERT INTO WorkoutDiary (workout_name, date_completed) VALUES (?, CURRENT_TIMESTAMP)";

        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, workoutName);
            stmt.executeUpdate();

            System.out.println("Workout " + workoutName + " marked as completed and saved to the diary.");
        } catch (SQLException e) {
            System.err.println("Error saving workout to diary: " + e.getMessage());
        }
    }

    @FXML
    private ImageView HelpImageView;

    @FXML
    protected void handleHelp() throws IOException {
        Stage stage = (Stage) HelpImageView.getScene().getWindow();
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/FitnessStrengthNew.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setScene(scene);
    }

    @FXML
    protected void NavHIIT() throws IOException {
        Stage stage = (Stage) HIITButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/FitnessHIITNew.fxml"));
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
