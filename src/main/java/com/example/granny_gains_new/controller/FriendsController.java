package com.example.granny_gains_new.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;

public class FriendsController {

    @FXML
    private VBox friendInputBox;

    @FXML
    private TextField AddFriend;

    @FXML
    private TextField AddAge;

    @FXML
    private TextField AddPhoneNumber;

    @FXML
    private TextField AddLikes;

    @FXML
    private VBox friendsVBox;

    @FXML
    private Button BackButton;

    @FXML
    protected void showInputFields() {
        friendInputBox.setVisible(true);
    }

    @FXML
    protected void hideInputFields() {
        friendInputBox.setVisible(false);
    }

    @FXML
    protected void handleToAddFriend() {
        String friendName = AddFriend.getText().trim();
        String age = AddAge.getText().trim();
        String phoneNumber = AddPhoneNumber.getText().trim();
        String likes = AddLikes.getText().trim();

        if (!friendName.isEmpty() && !age.isEmpty() && !phoneNumber.isEmpty() && !likes.isEmpty()) {
            TitledPane newFriendPane = new TitledPane();
            newFriendPane.setText(friendName);
            newFriendPane.setPrefWidth(310);
            newFriendPane.setStyle("-fx-background-color: white; -fx-border-color: #818589;");

            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setContrast(1.0);
            newFriendPane.setEffect(colorAdjust);

            HBox friendContainer = new HBox();
            friendContainer.setSpacing(10);

            TextFlow phoneFlow = new TextFlow();
            Text phoneLabel = new Text("Phone: ");
            phoneLabel.setStyle("-fx-font-weight: bold;");
            Text phoneText = new Text(phoneNumber);
            phoneFlow.getChildren().addAll(phoneLabel, phoneText);

            TextFlow ageFlow = new TextFlow();
            Text ageLabel = new Text("Age: ");
            ageLabel.setStyle("-fx-font-weight: bold;");
            Text ageText = new Text(age + " years old");
            ageFlow.getChildren().addAll(ageLabel, ageText);

            TextFlow likesFlow = new TextFlow();
            Text likesLabel = new Text("Likes: ");
            likesLabel.setStyle("-fx-font-weight: bold;");
            Text likesText = new Text(likes);
            likesFlow.getChildren().addAll(likesLabel, likesText);

            friendContainer.getChildren().addAll(phoneFlow, ageFlow, likesFlow);

            Button deleteButton = new Button("X");
            deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 30px;");
            deleteButton.setOnAction(e -> handleDeleteFriend(newFriendPane));

            friendContainer.getChildren().add(deleteButton);
            newFriendPane.setContent(friendContainer);
            friendsVBox.getChildren().add(newFriendPane);

            AddFriend.clear();
            AddAge.clear();
            AddPhoneNumber.clear();
            AddLikes.clear();
            friendInputBox.setVisible(false);
        }
    }

    private void handleDeleteFriend(TitledPane friendPane) {
        friendsVBox.getChildren().remove(friendPane);
    }

    @FXML
    protected void handleBackToHome() throws IOException {
        Stage stage = (Stage) BackButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/granny_gains_home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        stage.setScene(scene);
    }
}
