package com.example.granny_gains_new.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.example.granny_gains_new.model.User;
import com.example.granny_gains_new.database.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUpControllerTest {

    private SignUpController signUpController;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        // Establish a database connection (SQLite in-memory for testing)
        connection = DatabaseConnection.getInstance();
        signUpController = new SignUpController();

        // Create the user table in the database if not already created
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS User (" +
                    "email TEXT PRIMARY KEY, " +
                    "password TEXT NOT NULL, " +
                    "first_name TEXT, " +
                    "last_name TEXT, " +
                    "secret_answer TEXT, " +
                    "date_of_birth DATE, " +
                    "gender TEXT, " +
                    "height REAL, " +
                    "weight REAL, " +
                    "bmi REAL)");
        }
    }

    @Test
    void testInsertUserIntoDatabase() throws SQLException {
        // Arrange
        User user = new User("john@example.com", "password123", "John", "Doe");

        // Act
        signUpController.insertUserIntoDatabase(user);

        // Assert: Verify the user was successfully inserted into the database
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM User WHERE email = ?")) {
            stmt.setString(1, "john@example.com");
            ResultSet resultSet = stmt.executeQuery();
            assertTrue(resultSet.next());
            assertEquals("john@example.com", resultSet.getString("email"));
            assertEquals("password123", resultSet.getString("password"));
            assertEquals("John", resultSet.getString("first_name"));
            assertEquals("Doe", resultSet.getString("last_name"));
        }
    }

    @Test
    void testInsertUserWithDuplicateEmail() throws SQLException {
        // Arrange: Insert a user with a specific email
        User firstUser = new User("john@example.com", "password123", "John", "Doe");
        User duplicateUser = new User("john@example.com", "differentpassword", "Jane", "Smith");

        // Act: Insert the first user
        assertDoesNotThrow(() -> signUpController.insertUserIntoDatabase(firstUser));

        // Act: Attempt to insert a duplicate user with the same email
        assertDoesNotThrow(() -> signUpController.insertUserIntoDatabase(duplicateUser));

        // Assert: Verify that only one user with the email "john@example.com" exists in the database
        Connection connection = DatabaseConnection.getInstance();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM User WHERE email = ?")) {
            stmt.setString(1, "john@example.com");
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                assertEquals(1, count, "There should only be one user with this email");
            }
        }
    }

    @Test
    void testSignUpWithEmptyEmailAndPassword() {
        // Arrange: Create a user with an empty email and password
        User user = new User("", "", "John", "Doe");

        // Act: Try inserting the user into the database
        signUpController.insertUserIntoDatabase(user);

        // Assert: Query the database to ensure the user was not inserted
        Connection connection = DatabaseConnection.getInstance();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM User WHERE first_name = ? AND last_name = ?")) {
            stmt.setString(1, "John");
            stmt.setString(2, "Doe");
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                assertEquals(0, count, "User should not be inserted with empty email and password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Database query failed");
        }
    }

    @Test
    void testSignUpWithInvalidEmailFormat() {
        // Arrange: Create a user with an invalid email format
        User user = new User("invalid-email", "password123", "John", "Doe");

        // Act: Try inserting the user into the database
        signUpController.insertUserIntoDatabase(user);

        // Assert: Query the database to ensure the user was not inserted
        Connection connection = DatabaseConnection.getInstance();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM User WHERE email = ?")) {
            stmt.setString(1, "invalid-email");
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                assertEquals(0, count, "User should not be inserted with an invalid email format");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Database query failed");
        }
    }

    //This tests a successful user sign up
    @Test
    void testSuccessfulUserSignUp() throws SQLException {
        // Arrange: Create a valid user
        User user = new User("jane@example.com", "password123", "Jane", "Doe");

        // Act: Insert the user into the database
        signUpController.insertUserIntoDatabase(user);

        // Assert: Query the database to ensure the user was successfully inserted
        Connection connection = DatabaseConnection.getInstance();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM User WHERE email = ?")) {
            stmt.setString(1, "jane@example.com");
            ResultSet resultSet = stmt.executeQuery();
            assertTrue(resultSet.next(), "User should be successfully inserted into the database");
            assertEquals("jane@example.com", resultSet.getString("email"));
            assertEquals("password123", resultSet.getString("password"));
            assertEquals("Jane", resultSet.getString("first_name"));
            assertEquals("Doe", resultSet.getString("last_name"));
        }
    }

}
