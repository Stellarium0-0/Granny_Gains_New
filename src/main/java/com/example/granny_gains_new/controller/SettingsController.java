package com.example.granny_gains_new.controller;

import com.example.granny_gains_new.database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SettingsController {

    @FXML
    public void initialize() {

        loadNameFromSession();
        loadEmailFromSession();
        loadDOBFromSession();
    }
    @FXML
    private Button backButton;



    @FXML
    protected void handleBackToHome() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        double width = stage.getWidth();
        double height = stage.getHeight();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/granny_gains_home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setScene(scene);
    }

    @FXML
    private Button HelpButton;

    @FXML
    protected void handleToHelp() throws IOException {
        Stage stage = (Stage) HelpButton.getScene().getWindow();
        double width = stage.getWidth();
        double height = stage.getHeight();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/help_page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setScene(scene);
    }

    @FXML
    private Label FetchEmailLabel;

    @FXML
    private Label FetchNameLabel;

    @FXML
    private Label FetchDOBLabel;


    @FXML
    private void loadNameFromSession() {
        String userName = fetchUserNameFromSession();
        if (userName != null && !userName.isEmpty()) {
            FetchNameLabel.setText( userName );
        } else {
            FetchNameLabel.setText("Guest!");
        }
    }


    private String fetchUserNameFromSession() {
        String userName = "";
        String query = "SELECT u.first_name, u.last_name FROM User u "
                + "JOIN sessions s ON u.email = s.user_id "
                + "ORDER BY s.login_time DESC LIMIT 1"; // Fetch the latest session

        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userName = rs.getString("first_name") + " " + rs.getString("last_name");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user name from session: " + e.getMessage());
        }
        return userName;
    }

    private String fetchUserEmailFromSession() {
        String email = "";
        String query = "SELECT u.email FROM User u "
                + "JOIN sessions s ON u.email = s.user_id "
                + "ORDER BY s.login_time DESC LIMIT 1"; // Fetch the latest session

        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                email = rs.getString("email"); // Fetch the email field
            }
        } catch (SQLException e) {
            System.err.println("Error fetching email from session: " + e.getMessage());
        }
        return email;
    }

    @FXML
    private void loadEmailFromSession() {
        String email = fetchUserEmailFromSession();
        if (email != null && !email.isEmpty()) {
            FetchEmailLabel.setText( email );
        } else {
            FetchEmailLabel.setText("Guest!");
        }
    }

    private LocalDate fetchUserDOBFromSession() {
        LocalDate dateOfBirth = null;
        String query = "SELECT u.date_of_birth FROM User u "
                + "JOIN sessions s ON u.email = s.user_id "
                + "ORDER BY s.login_time DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dateOfBirth = rs.getDate("date_of_birth").toLocalDate();
            }
        } catch (SQLException e) {
            System.err.println("Error fetching date of birth from session: " + e.getMessage());
        }
        return dateOfBirth;
    }

    @FXML
    private void loadDOBFromSession() {
        LocalDate dateOfBirth = fetchUserDOBFromSession();
        if (dateOfBirth != null) {
            FetchDOBLabel.setText(dateOfBirth.toString());
        } else {
            FetchDOBLabel.setText("Date of Birth not available!");
        }
    }



}

