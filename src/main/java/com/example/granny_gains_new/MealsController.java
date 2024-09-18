package com.example.granny_gains_new;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;


public class MealsController {

    @FXML
    private ImageView meal1Image;

    @FXML
    private ImageView meal2Image;

    @FXML
    private ImageView meal3Image;

    @FXML
    private Button backButton;

    @FXML
    public void initialize() {
        // Load the images for the meals
        meal1Image.setImage(new Image(getClass().getResourceAsStream("/com/example/granny_gains_new/images/meal1.jpg")));
        meal2Image.setImage(new Image(getClass().getResourceAsStream("/com/example/granny_gains_new/images/meal2.jpg")));
        meal3Image.setImage(new Image(getClass().getResourceAsStream("/com/example/granny_gains_new/images/meal3.jpg")));
    }

    @FXML
    protected void handleBackToHome() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("granny_gains_home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 1000);
        stage.setScene(scene);
    }
}
