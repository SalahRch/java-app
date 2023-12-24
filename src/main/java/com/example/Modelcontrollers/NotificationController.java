package com.example.Modelcontrollers;

import com.example.Models.User;
import com.example.Models.Notification;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationController {
    private Connection connection;

    public NotificationController() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/socialmedia", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createNotification(String type, User sender, User receiver, String content) {
        String sql = "INSERT INTO notifications (type, sender_id, receiver_id, content) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, type);
            statement.setInt(2, sender.user_id(sender.getUsername()));
            statement.setInt(3, receiver.user_id(receiver.getUsername()));
            statement.setString(4, content);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Notification> getNotifications(User receiver) {
        List<Notification> userNotifications = new ArrayList<>();
        String sql = "SELECT * FROM Notifications WHERE receiver_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, receiver.user_id(receiver.getUsername()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String type = resultSet.getString("type");
                User sender = User.getUserbyID((resultSet.getInt("sender_id")));
                String content = resultSet.getString("content");
                userNotifications.add(new Notification(type, sender, receiver, content));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userNotifications;
    }
    public void deleteNotification(Notification item) {
        String sql = "DELETE FROM notifications WHERE sender_id = ? AND receiver_id = ? AND type = ? AND content = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, item.getSender().user_id(item.getSender().getUsername()));
            statement.setInt(2, item.getReceiver().user_id(item.getReceiver().getUsername()));
            statement.setString(3, item.getType());
            statement.setString(4, item.getContent());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean hasNoNotifs(User receiver){
        //check if receiver has a notif or no
        String sql = "SELECT * FROM Notifications WHERE receiver_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, receiver.user_id(receiver.getUsername()));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
