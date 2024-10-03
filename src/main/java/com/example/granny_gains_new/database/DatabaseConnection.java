package com.example.granny_gains_new.database;

import com.example.granny_gains_new.model.Recipe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for managing the database connection using the Singleton pattern.
 * It ensures that only one instance of the database connection is created and used across the application.
 * The database schema is executed on the first connection to create the required tables.
 */
public class DatabaseConnection {

    // Singleton instance of the Connection object.
    private static Connection instance = null;

    // SQLite database URL
    private static final String DATABASE_URL = "jdbc:sqlite:database.db"; // Update with the correct path if needed

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
                    " secret_answer TEXT, " +
                    " date_of_birth DATE, " +
                    " gender TEXT, " +
                    " height REAL, " +
                    " weight REAL, " +
                    " bmi REAL " +
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
                    " picture_url TEXT, " +
                    " is_favourited BOOLEAN DEFAULT 0 " + // Add this line
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
                    "); " +
                    // Favorites Table
                    "CREATE TABLE IF NOT EXISTS Favorites (" +
                    " user_id TEXT, " +
                    " recipe_id INTEGER, " +
                    " PRIMARY KEY (user_id, recipe_id), " +
                    " FOREIGN KEY (user_id) REFERENCES User(email), " +
                    " FOREIGN KEY (recipe_id) REFERENCES Recipe(recipe_id) " +
                    "); ";


    // Private constructor to prevent instantiation
    private DatabaseConnection() {}

    /**
     * This method returns the singleton instance of the Connection.
     * It establishes a new connection if one does not already exist or if it is closed.
     *
     * @return Connection instance - the singleton database connection.
     */
    public static synchronized Connection getInstance() {
        try {
            if (instance == null || instance.isClosed()) {
                // Establish connection to SQLite database
                instance = DriverManager.getConnection(DATABASE_URL);
                System.out.println("Database connected successfully!");

                // Create tables if they do not exist
                createTables(instance);
            }
        } catch (SQLException sqlEx) {
            System.err.println("Failed to connect to the database: " + sqlEx.getMessage());
        }
        return instance;
    }

    /**
     * This method executes the SQL schema to create the necessary tables in the database.
     * It is called after the connection to the database is established.
     *
     * @param connection The connection to the SQLite database.
     */
    private static void createTables(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLES_SQL);
            System.out.println("Database schema executed, tables created.");
        } catch (SQLException e) {
            System.err.println("Failed to create tables: " + e.getMessage());
        }
    }

    /**
     * Adds a recipe to the favorites for a user.
     *
     * @param userId    The user's email.
     * @param recipeId  The ID of the recipe to be added to favorites.
     */
    public void addFavorite(String userId, int recipeId) {
        String sql = "INSERT INTO Favorites (user_id, recipe_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = instance.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setInt(2, recipeId);
            pstmt.executeUpdate();
            System.out.println("Recipe added to favorites.");
        } catch (SQLException e) {
            System.err.println("Failed to add favorite: " + e.getMessage());
        }
    }

    /**
     * Removes a recipe from the favorites for a user.
     *
     * @param userId    The user's email.
     * @param recipeId  The ID of the recipe to be removed from favorites.
     */
    public void removeFavorite(String userId, int recipeId) {
        String sql = "DELETE FROM Favorites WHERE user_id = ? AND recipe_id = ?";
        try (PreparedStatement pstmt = instance.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setInt(2, recipeId);
            pstmt.executeUpdate();
            System.out.println("Recipe removed from favorites.");
        } catch (SQLException e) {
            System.err.println("Failed to remove favorite: " + e.getMessage());
        }
    }

    public List<Recipe> getFavorites(String userId) {
        List<Recipe> favoriteRecipes = new ArrayList<>();
        String sql = "SELECT r.* FROM Recipe r INNER JOIN Favorites f ON r.recipe_id = f.recipe_id WHERE f.user_id = ? AND f.is_favorited = 1";
        try (PreparedStatement pstmt = instance.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int recipeId = rs.getInt("recipe_id");
                String recipeName = rs.getString("recipe_name");
                // Retrieve other fields as needed

                Recipe recipe = new Recipe(recipeId, recipeName);
                // Set other fields of the recipe object

                favoriteRecipes.add(recipe);
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve favorites: " + e.getMessage());
        }
        return favoriteRecipes;
    }


    public void toggleFavorite(String userId, int recipeId, boolean isFavorited) {
        String sql = "INSERT INTO Favorites (user_id, recipe_id, is_favorited) VALUES (?, ?, ?) " +
                "ON CONFLICT(user_id, recipe_id) DO UPDATE SET is_favorited = ?";
        try (PreparedStatement pstmt = instance.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setInt(2, recipeId);
            pstmt.setInt(3, isFavorited ? 1 : 0); // Convert boolean to int
            pstmt.setInt(4, isFavorited ? 1 : 0);
            pstmt.executeUpdate();
            System.out.println("Favorite status updated.");
        } catch (SQLException e) {
            System.err.println("Failed to toggle favorite: " + e.getMessage());
        }
    }


    /**
     * This method closes the database connection.
     * It should be called when the application is shutting down to release the database resources.
     */
    public static synchronized void closeConnection() {
        if (instance != null) {
            try {
                if (!instance.isClosed()) {
                    instance.close();
                    System.out.println("Database connection closed.");
                }
            } catch (SQLException e) {
                System.err.println("Error closing the database connection: " + e.getMessage());
            }
        }
    }

    public class FavouritesConnection {
        private static final String URL = "jdbc:sqlite:favourites.db"; // Database file location

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL);
        }
    }
}
