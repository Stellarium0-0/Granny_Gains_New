package com.example.granny_gains_new.model;

import com.example.granny_gains_new.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavouritesManager {

    // Initialize the database table if it doesn't exist
    static {
        try (Connection connection = DatabaseConnection.FavouritesConnection.getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS favourites (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "category TEXT NOT NULL, " +
                    "UNIQUE(name, category)" + // Prevent duplicates
                    ")";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a method to add a favourited item
    public static void addFavouritedItem(String name, String category) {
        String sql = "INSERT OR IGNORE INTO favourites (name, category) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.FavouritesConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, category);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a method to remove a favourited item
    public static void removeFavouritedItem(String name, String category) {
        String sql = "DELETE FROM favourites WHERE name = ? AND category = ?";
        try (Connection connection = DatabaseConnection.FavouritesConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, category);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all favourited items
    public static List<FavouritedItem> getFavourites() {
        List<FavouritedItem> favourites = new ArrayList<>();
        String sql = "SELECT name, category FROM favourites";
        try (Connection connection = DatabaseConnection.FavouritesConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String name = rs.getString("name");
                String category = rs.getString("category");
                favourites.add(new FavouritedItem(name, category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favourites;
    }
}
