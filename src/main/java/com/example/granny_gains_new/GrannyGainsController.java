package com.example.granny_gains_new;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.event.ActionEvent;

import java.io.IOException;


public class GrannyGainsController {

    @FXML
    private Label welcomeText;
    @FXML
    private TextArea termsAndConditions;
    @FXML
    private CheckBox agreeCheckBox;
    @FXML
    private Button nextButton;

    @FXML
    public void initialize() {
        termsAndConditions.setText("""
    
            We collect and store the following information:
                            
            Personal Information: Your email address and password are required for account creation and login. 
            We use this information to authenticate your account and communicate with you regarding App updates 
            or relevant information.

            Health Metrics: The App tracks health-related data such as fitness activities, dietary habits, 
            and other metrics. This data is used to provide personalized insights and recommendations aimed 
            at improving your health and fitness.
    """);
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to Granny Gains!");
    }

    @FXML
    protected void onAgreeCheckBoxClick() {
        boolean accepted = agreeCheckBox.isSelected();
        nextButton.setDisable(!accepted);
    }

    @FXML
    protected void onNextButtonClick() throws IOException {
        Stage stage = (Stage) nextButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sign_in_page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
    }

    @FXML
    private void handleNextButton(ActionEvent event) {
        try {
            // Load the home FXML file directly
            Parent homeRoot = FXMLLoader.load(getClass().getResource("sign_in_page.fxml"));

            // Get the current stage
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Set the new scene to the stage
            stage.setScene(new Scene(homeRoot, 1200, 650));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
