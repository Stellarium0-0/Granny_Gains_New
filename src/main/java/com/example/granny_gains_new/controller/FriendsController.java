package com.example.granny_gains_new.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class FriendsController {

    @FXML
    private Button BackButton;

    @FXML
    private Button AddFriendButton;

    @FXML
    private TextField AddFriend;

    @FXML
    private VBox friendsVBox;

    // to home page
    @FXML
    protected void handleBackToHome() throws IOException {
        Stage stage = (Stage) BackButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/granny_gains_home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        stage.setScene(scene);
    }



    @FXML
    public void initialize() {
        AddFriendButton.setOnAction(event -> addFriend());
    }

    // VBOX
    private void addFriend() {
        String friendName = AddFriend.getText();

        // Only add if text is not null
        if (friendName != null && !friendName.trim().isEmpty()) {
            // Create a pane
            TitledPane newFriendPane = new TitledPane();
            newFriendPane.setText(friendName);
            newFriendPane.setPrefHeight(26.0);
            newFriendPane.setPrefWidth(333.0);
            newFriendPane.setStyle("-fx-background-color: #D1FF98; -fx-border-color: #818589;");

            // Add input to box
            friendsVBox.getChildren().add(newFriendPane);

            AddFriend.clear();
        }
    }
}
