package com.example.chat.model;
import com.example.Models.User;
import com.example.chat.AddUtilisateurController;
import com.example.session.UserSession;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppQuery {
    public User userz = new User();
    public int id = userz.user_id(UserSession.getUsername());
    private DatabaseConnector conn=new DatabaseConnector();
    public static Utilisateur Me=new Utilisateur(UserSession.getUsername(),UserSession.getEmail());
//    private void addUtilisateur(Utilisateur new_utilisateur){
//        try {
//            Connection conn= DatabaseConnector.connect();
//            String query = "INSERT INTO users (username, Email, password, avatar, date_inscription) VALUES (?, ?, ?, ?, ?)";
//
//            // Creating a PreparedStatement
//            PreparedStatement preparedStatement = conn.prepareStatement(query);
//
//            // Set values for the query parameters
//            preparedStatement.setString(1, new_utilisateur.getUsername());
//            preparedStatement.setString(2, new_utilisateur.getEmail());
//            preparedStatement.setString(3, new_utilisateur.getPassword());
//            preparedStatement.setBlob(4, new_utilisateur.getPdp()); // Assuming getAvatar() returns the path or byte array of the image
//            preparedStatement.setDate(5, new_utilisateur.getDate());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
    // Method to search for users by username
    public List<Utilisateur> searchUtilisateur(String searchText) {
        List<Utilisateur> foundUsers = new ArrayList<>();

        try {
            Connection connection = conn.connect();
            String query = "SELECT * FROM users WHERE Username LIKE ? AND Username <> ? " +
                    "AND User_ID NOT IN " +
                    "(SELECT F.followed_id FROM following F WHERE F.user_id = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchText + "%");
            preparedStatement.setString(2, Me.getUsername()); // Replace with appropriate username fetching
            preparedStatement.setInt(3, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id=resultSet.getInt("User_ID");
                String username = resultSet.getString("Username");
                String email = resultSet.getString("Email");
                String avatar = resultSet.getString("Profile_Picture_URL");
                Date date = resultSet.getDate("Join_Date");

                // Create a Utilisateur object
                Utilisateur user = new Utilisateur(id,username, email, avatar);
                foundUsers.add(user);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return foundUsers;
    }
    public void addFriend(int userId, int friendId) {
        try (Connection connection = conn.connect()) {
            String query = "INSERT INTO following (user_id, followed_id) VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, friendId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Friendship added successfully!");
            } else {
                System.out.println("Failed to add friendship.");
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exceptions appropriately
        }
    }

    public int getUserIdByUsername(String username) {
        int userId = -1; // Initialize with a default value indicating user not found

        try (Connection connection = conn.connect()) {
            String query = "SELECT User_ID FROM users WHERE Username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getInt("User_ID");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exceptions appropriately
        }

        return userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
