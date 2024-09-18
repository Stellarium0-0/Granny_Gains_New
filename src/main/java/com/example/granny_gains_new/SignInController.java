package com.example.granny_gains_new;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class SignInController {
    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private Button ButtonSignin;

    @FXML
    private Label lblwrongPassword;

    @FXML
    protected void handleSignIn() {

        String email = tfEmail.getText();
        String password = tfPassword.getText();

        // assume the validation is successful
        if (email.equals("user") && password.equals("password")) {
            try {
                // Load the Granny Gains home page
                Stage stage = (Stage) ButtonSignin.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("granny_gains_home.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            lblwrongPassword.setText("Invalid credentials. Please try again.");
        }
    }

    @FXML
    protected void buttonSignup() {
        try {

            Stage stage = (Stage) ButtonSignin.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sign_up_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 650);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
