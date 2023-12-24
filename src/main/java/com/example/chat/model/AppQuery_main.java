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

public class AppQuery_main {
    public static User userz = new User();
    public static int id = userz.user_id(UserSession.getUsername());
    private static DatabaseConnector conn=new DatabaseConnector();
    public static Utilisateur Me=new Utilisateur(UserSession.getUsername(),UserSession.getEmail());
    public static List<Utilisateur> searchUtilisateur(String searchText) {
        List<Utilisateur> foundUsers = new ArrayList<>();

        try {
            Connection connection = conn.connect();
            String query = "SELECT * FROM users WHERE Username LIKE ? AND Username <> ? " +
                    "AND User_ID  IN " +
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
    public static List<Message> getMessagesBetweenUsers(int user1Id, int user2Id) {
        List<Message> messages = new ArrayList<>();
        System.out.println("id="+user1Id+"and" +user2Id);
        try { Connection connection = conn.connect();
            String query = "SELECT * FROM messages " +
                    "WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) " +
                    "ORDER BY timestamp"; // Query to fetch messages between two users
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user1Id);
            preparedStatement.setInt(2, user2Id);
            preparedStatement.setInt(3, user2Id);
            preparedStatement.setInt(4, user1Id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Assuming Message class has necessary attributes and constructor
                Message message = new Message(
                        resultSet.getInt("message_id"),
                        resultSet.getInt("sender_id"),
                        resultSet.getInt("receiver_id"),
                        resultSet.getString("message_content"),
                        resultSet.getTimestamp("timestamp")
                );
                messages.add(message);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return messages;
    }
    public static boolean saveMessageToDatabase(int senderId, int receiverId, String messageText) {
        try {
            // Perform the database insert operation here using JDBC or your preferred ORM framework
            Connection connection = conn.connect();

            // Example using JDBC PreparedStatement with CURRENT_TIMESTAMP
            String insertQuery = "INSERT INTO messages (sender_id, receiver_id, message_content) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, senderId);
            preparedStatement.setInt(2, receiverId);
            preparedStatement.setString(3, messageText);

            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected > 0) {
                // Inserted successfully
                return true;
            } else {
                // Failed to insert
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Return false if there was an error while inserting data
            return false;
        }
    }

}
