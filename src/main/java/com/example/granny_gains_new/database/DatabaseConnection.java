package com.example.granny_gains_new.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is responsible for managing the database connection using the Singleton pattern.
 * It ensures that only one instance of the database connection is created and used across the application.
 * The database schema is executed on the first connection to create the required tables.
 */
public class DatabaseConnection {

    // Volatile keyword ensures visibility across threads
    private static volatile Connection instance;

    // SQLite database URL
    private static final String DATABASE_URL = "jdbc:sqlite:database.db";

    /**
     * SQL schema for creating the necessary tables in the database.
     * This will create tables if they do not exist already.
     */
    private static final String CREATE_TABLES_SQL =
            // User Table
            "CREATE TABLE IF NOT EXISTS User (" +
                    " email TEXT PRIMARY KEY, " +
                    " password TEXT NOT NULL, " +
                    " first_name TEXT, " +
                    " last_name TEXT, " +
                    " phone REAL, " +
                    " date_of_birth DATE, " +
                    " gender TEXT, " +
                    " height REAL, " +
                    " weight REAL, " +
                    " bmi REAL, " +
                    " secret_question TEXT, " +
                    " secret_answer TEXT " +
                    "); " +
                    // Workout Table
                    "CREATE TABLE IF NOT EXISTS Workout (" +
                    " workout_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " user_id TEXT, " +
                    " date DATE, " +
                    " workout_type TEXT, " +
                    " duration INTEGER, " +
                    " calories_burned INTEGER, " +
                    " FOREIGN KEY (user_id) REFERENCES User(email) " +
                    "); " +
                    // Program Table
                    "CREATE TABLE IF NOT EXISTS Program (" +
                    " program_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " name TEXT, " +
                    " description TEXT, " +
                    " program_video_url TEXT, " +
                    " difficulty_level TEXT, " +
                    " program_type TEXT, " +
                    " instructions TEXT " +
                    "); " +
                    // Workout Log
                    "CREATE TABLE IF NOT EXISTS WorkoutDiary (" +
                    "workout_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "workout_name TEXT NOT NULL," +
                    "date_completed TIMESTAMP" +
                    ");" +
                    // User Program Session Table
                    "CREATE TABLE IF NOT EXISTS User_program_session (" +
                    " user_program_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " user_id TEXT, " +
                    " program_id INTEGER, " +
                    " start_date DATE, " +
                    " end_date DATE, " +
                    " status TEXT, " +
                    " FOREIGN KEY (user_id) REFERENCES User(email), " +
                    " FOREIGN KEY (program_id) REFERENCES Program(program_id) " +
                    "); " +
                    // Meal Plan Table
                    "CREATE TABLE IF NOT EXISTS Meal_plan (" +
                    " meal_plan_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " user_id TEXT, " +
                    " date DATE, " +
                    " FOREIGN KEY (user_id) REFERENCES User(email) " +
                    "); " +
                    // Recipe Table
                    "CREATE TABLE IF NOT EXISTS Recipe (" +
                    " recipe_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " recipe_type TEXT, " +
                    " recipe_name TEXT, " +
                    " servings INTEGER, " +
                    " calories INTEGER, " +
                    " description TEXT, " +
                    " ingredients TEXT, " +
                    " recipe_method TEXT, " +
                    " picture_url TEXT " +
                    "); " +
                    // Meal Plan Recipe Table
                    "CREATE TABLE IF NOT EXISTS Meal_plan_recipe (" +
                    " meal_plan_id INTEGER, " +
                    " recipe_id INTEGER, " +
                    " FOREIGN KEY (meal_plan_id) REFERENCES Meal_plan(meal_plan_id), " +
                    " FOREIGN KEY (recipe_id) REFERENCES Recipe(recipe_id) " +
                    ");" +
                    // Session Table
                    "CREATE TABLE IF NOT EXISTS sessions (" +
                    " session_id TEXT PRIMARY KEY, " +
                    " user_id INTEGER, " +
                    " login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    " FOREIGN KEY (user_id) REFERENCES User(email) " +
                    "); ";

    // Private constructor to prevent instantiation
    private DatabaseConnection() {}

    /**
     * This method returns the singleton instance of the Connection.
     * It establishes a new connection if one does not already exist or if it is closed.
     *
     * @return Connection instance - the singleton database connection.
     * @throws RuntimeException if a database access error occurs
     */
    public static Connection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    try {
                        instance = DriverManager.getConnection(DATABASE_URL);
                        createTables();
                        System.out.println("Database connected successfully!");
                    } catch (SQLException e) {
                        System.err.println("Failed to connect to the database: " + e.getMessage());
                        throw new RuntimeException("Database connection failed", e);
                    }
                }
            }
        }
        return instance;
    }

    /**
     * This method executes the SQL schema to create the necessary tables in the database.
     * It is called after the connection to the database is established.
     *
     * @throws RuntimeException if a database access error occurs
     */
    private static void createTables() {
        try (Statement statement = instance.createStatement()) {
            statement.executeUpdate(CREATE_TABLES_SQL);
            System.out.println("Database schema executed, tables created.");
        } catch (SQLException e) {
            System.err.println("Failed to create tables: " + e.getMessage());
            throw new RuntimeException("Failed to create tables", e);
        }
    }

    /**
     * This method closes the database connection.
     * It should be called when the application is shutting down to release the database resources.
     */
    public static void closeConnection() {
        if (instance != null) {
            try {
                instance.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing the database connection: " + e.getMessage());
            } finally {
                instance = null;
            }
        }
    }
}