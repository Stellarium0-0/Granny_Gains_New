package com.example.granny_gains_new.controller;

import com.example.granny_gains_new.database.DatabaseConnection;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;




import static org.junit.jupiter.api.Assertions.assertEquals;


public class FitnessControllerTest extends ApplicationTest {

    private FitnessController controller;
    private ImageView imageView;
    private Label titleLabel;
    private Button CardioButton;

    @BeforeEach
    public void setUp() throws Exception {
        controller = new FitnessController();
        imageView = new ImageView();
        titleLabel = new Label();

        // Set the in-memory database URL and establish the connection only once
        DatabaseConnection.setDatabaseUrl("jdbc:sqlite::memory:");
        DatabaseConnection.getInstance(); // Initializes the schema
    }

    @AfterEach
    public void tearDown() {
        DatabaseConnection.closeConnection(); // Close the connection after each test
    }

    @Test
    public void testUpdateWorkoutTileLogic() {
        String title = "Morning Cardio";
        String imagePath = "/com/example/granny_gains_new/images/morning_cardio.png";
        String videoLink = "http://example.com/video";

        controller.updateWorkoutTile(imageView, titleLabel, title, imagePath, videoLink);

        // Check that the title has been set
        assertEquals("Morning Cardio", titleLabel.getText());
    }

}
