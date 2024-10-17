package com.example.granny_gains_new.controller;

import com.example.granny_gains_new.database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView.TableViewSelectionModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FitnesslogController {

    @FXML
    TableView<WorkoutEntry> diaryTableView;

    @FXML
    TableColumn<WorkoutEntry, String> workoutNameColumn;

    @FXML
    TableColumn<WorkoutEntry, String> dateCompletedColumn;

    @FXML
    private Button backButton;

    @FXML
    public void initialize() {
        workoutNameColumn.setCellValueFactory(new PropertyValueFactory<>("workoutName"));
        dateCompletedColumn.setCellValueFactory(new PropertyValueFactory<>("dateCompleted"));
        loadCompletedWorkouts();
    }

    private void loadCompletedWorkouts() {
        String query = "SELECT workout_name, date_completed FROM WorkoutDiary ORDER BY date_completed DESC";

        ObservableList<WorkoutEntry> completedWorkouts = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String workoutName = rs.getString("workout_name");
                String dateCompleted = rs.getTimestamp("date_completed").toString();
                completedWorkouts.add(new WorkoutEntry(workoutName, dateCompleted));
            }

            diaryTableView.setItems(completedWorkouts);
        } catch (SQLException e) {
            System.err.println("Error loading completed workouts: " + e.getMessage());
        }
    }

    @FXML
    private void handleBack() throws Exception {
        Stage stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/granny_gains_new/fitness_page.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 800);
        stage.setScene(scene);
    }

    public static class WorkoutEntry {
        private final String workoutName;
        private final String dateCompleted;

        public WorkoutEntry(String workoutName, String dateCompleted) {
            this.workoutName = workoutName;
            this.dateCompleted = dateCompleted;
        }

        public String getWorkoutName() {
            return workoutName;
        }

        public String getDateCompleted() {
            return dateCompleted;
        }
    }
}
