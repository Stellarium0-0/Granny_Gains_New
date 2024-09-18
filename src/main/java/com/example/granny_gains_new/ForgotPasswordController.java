package com.example.granny_gains_new;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class ForgotPasswordController {

    @FXML
    private TextField tfEmail;

    @FXML
    private Button ButtonEnterEmail;


    @FXML
    private Button BackToSignIn;


    @FXML
    protected void handleBackToSignIn() {


        try {
            Stage stage = (Stage) ButtonEnterEmail.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sign_in_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 650);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleEnterEmail() {

        System.out.println("Email is Correct");

        // After signing up, navigate back to the sign-in page
        try {
            Stage stage = (Stage) ButtonEnterEmail.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sign_in_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 650);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
