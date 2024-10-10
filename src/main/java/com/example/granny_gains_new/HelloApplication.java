package com.example.granny_gains_new;

import com.example.granny_gains_new.database.DatabaseConnection;
import com.example.granny_gains_new.database.RecipeDBHandler;
import com.example.granny_gains_new.database.RecipeLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;

public class HelloApplication extends Application {

    public static final String TITLE = "Granny Gains";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("granny_gains.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Database connection
        Connection connection = DatabaseConnection.getInstance();

        // Initialize the database handler
        RecipeDBHandler dbHandler = new RecipeDBHandler();

        // Initialize the recipe loader
        RecipeLoader loader = new RecipeLoader();

        // Path to your CSV file (update with your actual file path)
        String csvFilePath = "src/main/resources/com/example/granny_gains_new/database/meals.csv";

        // Load recipes from CSV (without duplicates) when the app starts
        loader.loadRecipesFromCSV(csvFilePath, dbHandler);
        //Launch JavaFX application

        launch();

    }
}
