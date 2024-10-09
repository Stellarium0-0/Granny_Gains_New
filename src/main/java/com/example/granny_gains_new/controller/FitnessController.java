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

/**
 * Controller class for handling the Fitness section in Granny Gains.
 * <p>
 * This class provides navigation between various fitness sections (Cardio, Strength, HIIT),
 * loads workout data from CSV files, and displays them in the Cardio section.
 * </p>
 */
public class FitnessController {

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
    private ImageView Cardio1, Cardio2, Cardio3, Cardio4;
    @FXML
    private Label Cardio1Title, Cardio2Title, Cardio3Title, Cardio4Title;

    /**
     * Initializes the controller by loading cardio workout data from a CSV file.
     */
    @FXML
    public void initialize() {
        loadCardioWorkouts();
    }

    /**
     * Navigates to the Fitness Help view.
     *
     * @throws IOException if the FXML file for the help view is not found.
     */
    @FXML
    protected void handleHelp() throws IOException {
        Stage stage = (Stage) HelpButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/fitness_help.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setScene(scene);
    }

    /**
     * Navigates to the Cardio workout view.
     *
     * @throws IOException if the FXML file for the Cardio view is not found.
     */
    @FXML
    protected void NavCardio() throws IOException {
        Stage stage = (Stage) CardioButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/FitnessCardio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setScene(scene);
    }

    /**
     * Navigates to the Strength workout view.
     *
     * @throws IOException if the FXML file for the Strength view is not found.
     */
    @FXML
    protected void NavStrength() throws IOException {
        Stage stage = (Stage) StrengthButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/FitnessStrength.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setScene(scene);
    }

    /**
     * Navigates to the HIIT workout view.
     *
     * @throws IOException if the FXML file for the HIIT view is not found.
     */
    @FXML
    protected void NavHIIT() throws IOException {
        Stage stage = (Stage) HIITButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/FitnessHIIT.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setScene(scene);
    }

    /**
     * Navigates back to the Home view.
     *
     * @throws IOException if the FXML file for the Home view is not found.
     */
    @FXML
    protected void handleBackToHome() throws IOException {
        Stage stage = (Stage) HomeButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/granny_gains_home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        stage.setScene(scene);
    }

    /**
     * Loads cardio workout data from a CSV file and updates the corresponding UI elements.
     * Reads from the file "fitness.csv" and displays the workouts in designated ImageView and Label components.
     */
    private void loadCardioWorkouts() {
        String csvFile = "src/main/java/com/example/granny_gains_new/database/fitness.csv";
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int counter = 1;
            while ((line = br.readLine()) != null) {
                if (counter == 1) {
                    counter++;
                    continue;
                }

                String[] workoutData = line.split(csvSplitBy);
                String title = workoutData[0].replace("\"", "").trim();
                String thumbnailPath = workoutData[1].replace("\"", "").trim();
                String videoLink = workoutData[2].replace("\"", "").trim();

                switch (counter) {
                    case 2:
                        updateWorkoutTile(Cardio1, Cardio1Title, title, thumbnailPath, videoLink);
                        break;
                    case 3:
                        updateWorkoutTile(Cardio2, Cardio2Title, title, thumbnailPath, videoLink);
                        break;
                    case 4:
                        updateWorkoutTile(Cardio3, Cardio3Title, title, thumbnailPath, videoLink);
                        break;
                    case 5:
                        updateWorkoutTile(Cardio4, Cardio4Title, title, thumbnailPath, videoLink);
                        break;
                }

                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a specified ImageView and Label with workout information and assigns an event handler for video playback.
     *
     * @param imageView the ImageView component to display the workout thumbnail.
     * @param titleLabel the Label component to display the workout title.
     * @param title the workout title to set in the Label.
     * @param imagePath the path to the workout thumbnail image.
     * @param videoLink the URL link to the workout video.
     */
    private void updateWorkoutTile(ImageView imageView, Label titleLabel, String title, String imagePath, String videoLink) {
        try {
            Image thumbnail = new Image(getClass().getResource(imagePath).toExternalForm());
            imageView.setImage(thumbnail);
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

        titleLabel.setText(title);

        imageView.setOnMouseClicked(event -> openVideoPlayer(videoLink));
        titleLabel.setOnMouseClicked(event -> openVideoPlayer(videoLink));
    }

    /**
     * Opens a new scene with a video player to play the specified workout video.
     *
     * @param videoUrl the URL of the workout video to play.
     */
    private void openVideoPlayer(String videoUrl) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/fitness_player.fxml"));
            Parent root = loader.load();

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

/**
 REPLACING DYNAMIC ADJUSTMENTS
 FXIDS in "src/main/resources/com/example/granny_gains_new/FitnessCardio.fxml"
 <--Labels-->
 Cardio1Title, Cardio2Title, Cardio3Title, Cardio4Title
 </--Labels-->
 <--Images (Thumbnail) -->
 Cardio1, Cardio2, Cardio3, Cardio4
 </--Labels-->
 */
