package com.example.granny_gains_new.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FitnesslogControllerTest extends ApplicationTest {

    private FitnesslogController controller;

    @Override
    public void start(Stage stage) {
        // Run UI setup
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/fitnesslog.fxml"));
                Parent root = loader.load();
                controller = loader.getController();

                // Set the scene on the stage and show
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Wait for the JavaFX thread to complete before proceeding
        WaitForAsyncUtils.waitForFxEvents();
    }

    @BeforeEach
    public void setUp() {

        assertNotNull(controller, "Controller should be initialized.");
    }

    @Test
    public void testLoadCompletedWorkouts() {
        // Add assertions verify that the table loads correctly
        Platform.runLater(() -> {

            assertNotNull(controller.diaryTableView.getItems(), "TableView should be populated.");

        });

        // Wait for any queued events to be processed
        WaitForAsyncUtils.waitForFxEvents();
    }
}
