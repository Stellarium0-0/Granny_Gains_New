package com.example.granny_gains_new.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class FriendsController {

    @FXML
    private Button BackButton;

    @FXML
    private TextField AddFriend;

    @FXML
    private VBox friendsVBox;

    private final Random random = new Random();

    private final String[] likesOptions = {
            "swimming", "walking", "running", "dancing", "weightlifting",
            "yoga", "aquatics", "stretching", "gardening", "biking"
    };

    @FXML
    protected void handleBackToHome() throws IOException {
        Stage stage = (Stage) BackButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/granny_gains_home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        stage.setScene(scene);
    }

    @FXML
    protected void handleToAddFriend() {
        String friendName = AddFriend.getText().trim();
        if (!friendName.isEmpty()) {
            TitledPane newFriendPane = new TitledPane();
            newFriendPane.setText(friendName);
            newFriendPane.setPrefWidth(310); // Set the width to 310
            newFriendPane.setStyle("-fx-background-color: white; -fx-border-color: #818589;");

            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setContrast(1.0);
            newFriendPane.setEffect(colorAdjust);

            VBox friendDetails = new VBox();
            friendDetails.setSpacing(5);


            String randomPhoneNumber = generateRandomPhoneNumber();


            int randomAge = random.nextInt(21) + 60;


            String randomLike = likesOptions[random.nextInt(likesOptions.length)];


            TextFlow phoneFlow = new TextFlow();
            Text phoneLabel = new Text("Phone: ");
            phoneLabel.setStyle("-fx-font-weight: bold;");
            Text phoneNumber = new Text(randomPhoneNumber);
            phoneFlow.getChildren().addAll(phoneLabel, phoneNumber);


            TextFlow ageFlow = new TextFlow();
            Text ageLabel = new Text("Age: ");
            ageLabel.setStyle("-fx-font-weight: bold;");
            Text age = new Text(randomAge + " years old");
            ageFlow.getChildren().addAll(ageLabel, age);


            TextFlow likesFlow = new TextFlow();
            Text likesLabel = new Text("Likes: ");
            likesLabel.setStyle("-fx-font-weight: bold;");
            Text likes = new Text(randomLike);
            likesFlow.getChildren().addAll(likesLabel, likes);

            friendDetails.getChildren().addAll(phoneFlow, ageFlow, likesFlow);

            newFriendPane.setContent(friendDetails);

            friendsVBox.getChildren().add(newFriendPane);

            AddFriend.clear();
        }
    }


    private String generateRandomPhoneNumber() {
        int part1 = 491;
        int part2 = random.nextInt(999);
        int part3 = random.nextInt(999);
        return String.format("0%d %03d %03d", part1, part2, part3);
    }
}
