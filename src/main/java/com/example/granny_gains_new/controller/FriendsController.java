package com.example.granny_gains_new.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    protected void handleBackToHome() throws IOException {
        Stage stage = (Stage) BackButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/granny_gains_home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        stage.setScene(scene);
    }


@FXML
protected void handleToAddFriend() throws IOException {
    Stage stage = (Stage) AddFriendButton.getScene().getWindow();
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/add_friends_page.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
    stage.setScene(scene);
}
}




