package com.example.granny_gains_new.controller;

import com.example.granny_gains_new.database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.CollationElementIterator;
import java.util.UUID;
import java.util.prefs.Preferences;


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
    private CheckBox rememberMeCheckBox;
    private Preferences preferences;

    public SignInController() {
        preferences = Preferences.userNodeForPackage(SignInController.class);
    }

    @FXML
    protected void initialize() {
        String storedEmail = preferences.get("email", "");
        String storedPassword = preferences.get("password", "");
        tfEmail.setText(storedEmail);
        tfPassword.setText(storedPassword);
        rememberMeCheckBox.setSelected(!storedEmail.isEmpty());
    }

    @FXML
    protected void handleSignIn() {
        String email = tfEmail.getText();
        String password = tfPassword.getText();

        if (validateCredentials(email, password)) {
            // Save email and password if "Remember Me" is checked
            if (rememberMeCheckBox.isSelected()) {
                preferences.put("email", email);
                preferences.put("password", password);
            } else {
                preferences.remove("email");
                preferences.remove("password");
            }

            // Generate session ID and load the home page
            String sessionId = UUID.randomUUID().toString();
            createSession(email, sessionId);
            try {
                Stage stage = (Stage) ButtonSignin.getScene().getWindow();
                double width = stage.getWidth();
                double height = stage.getHeight();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/granny_gains_home.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), width, height);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            lblwrongPassword.setText("Invalid credentials. Please try again.");
        }
    }

    // Method to validate credentials with the database
    // Check if the connection is open during sign-in validation
    public boolean validateCredentials(String email, String password) {
        try (Connection conn = DatabaseConnection.getInstance()) {
            if (conn == null || conn.isClosed()) {
                System.err.println("Database connection is closed.");
                return false;
            }

            String sql = "SELECT password FROM User WHERE email = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    return storedPassword.equals(password);
                } else {
                    return false;  // No user found
                }
            }
        } catch (SQLException e) {
            System.err.println("Error validating user credentials: " + e.getMessage());
            return false;
        }
    }

    // Method to create a session and store it in the database
    private void createSession(String email, String sessionId) {
        try (Connection conn = DatabaseConnection.getInstance()) {
            String query = "INSERT INTO sessions (session_id, user_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, sessionId);
            stmt.setString(2, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void buttonSignup() {
        try {
            Stage stage = (Stage) ButtonSignin.getScene().getWindow();
            double width = stage.getWidth();
            double height = stage.getHeight();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/sign_up_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), width,height);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleForgotPassword() {
        try {
            Stage stage = (Stage) ButtonSignin.getScene().getWindow();
            double width = stage.getWidth();
            double height = stage.getHeight();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/forgot_password_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), width, height);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
