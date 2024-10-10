package com.example.granny_gains_new.controller;

import com.example.granny_gains_new.database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotPasswordController {

    @FXML
    private TextField tfEmail;

    @FXML
    private Button ButtonEnterEmail;

    @FXML
    private Button BackToSignIn;

    @FXML
    private Label lblemail;


    @FXML
    protected void handleBackToSignIn() {


        try {
            Stage stage = (Stage) ButtonEnterEmail.getScene().getWindow();
            double width = stage.getWidth();
            double height = stage.getHeight();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/sign_in_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), width, height);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleEnterEmail() {

        if (tfEmail.getText().isEmpty()) {
            lblemail.setText("Please fill in Email");
            return;
        }

        validateCredentials(tfEmail.getText());
    }

    protected void validateCredentials(String email) {
        try (Connection conn = DatabaseConnection.getInstance()) {
            if (conn == null || conn.isClosed()) {
                System.err.println("Database connection is closed.");
                return;
            }

            String sql = "SELECT * FROM User WHERE email = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    try {
                        Stage stage = (Stage) ButtonEnterEmail.getScene().getWindow();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/password_question_page.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 1200, 650);
                        PasswordQuestionController controller = fxmlLoader.getController();
                        controller.setEmail(email);
                        stage.setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    lblemail.setText("Invalid Email");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error validating user credentials: " + e.getMessage());
        }
    }
}
