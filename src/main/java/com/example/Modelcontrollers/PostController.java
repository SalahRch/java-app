package com.example.Modelcontrollers;

import com.example.Models.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostController {
    private static final String URL = "jdbc:mysql://localhost:3306/socialmedia";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection conn = DatabaseController.connect()) {
            String sql = "SELECT * FROM Post";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Post post = new Post();
                post.setId(resultSet.getInt("id"));
                post.setContent(resultSet.getString("content"));
                post.setUserId(resultSet.getInt("userId"));
                post.setTimestamp(resultSet.getString("timestamp"));
                post.setFile(resultSet.getBytes("file"));
                post.setImage(resultSet.getBytes("image"));
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }
    public List<Post> posts(int user_id) {
        List<Post> posts = new ArrayList<>();
        try (Connection conn = DatabaseController.connect()) {
            String sql = "SELECT * FROM post WHERE userId = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Post post = new Post();
                post.setId(resultSet.getInt("id"));
                post.setContent(resultSet.getString("content"));
                post.setUserId(resultSet.getInt("userId"));
                post.setTimestamp(resultSet.getString("timestamp"));
                post.setFile(resultSet.getBytes("file"));
                post.setImage(resultSet.getBytes("image"));
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }
    public void createPost(String content, int userId, byte[] file, byte[] image) {


        if(content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        try (Connection conn = DatabaseController.connect()) {
            String sql = "INSERT INTO post (content, userId, file, image) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, content);
            statement.setInt(2, userId);
            if (file != null) {
                statement.setBytes(3, file);
            } else {
                statement.setNull(3, Types.BINARY);
            }
            if (image != null) {
                statement.setBytes(4, image);
            } else {
                statement.setNull(4, Types.BINARY);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePost(int id, String newContent) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE Post SET content = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, newContent);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePost(int id) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "DELETE FROM Post WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}