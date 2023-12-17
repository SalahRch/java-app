
package com.example.Modelcontrollers;

import com.example.Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FollowController {


    public void followUser(User follower, User followed) {
        if (!isFollowing(follower, followed)) {
            addFollowRecord(follower, followed);
            follower.incrementFollowingCount();
            followed.incrementFollowersCount();
            follower.setFollowing(follower.getFollowing() + 1);
            followed.setFollowers(followed.getFollowers() + 1);
        }
    }

    // Method to handle the unfollow action
    public void unfollowUser(User follower, User followed) throws SQLException {
        if (isFollowing(follower, followed)) {
            removeFollowRecord(follower, followed);
            follower.decrementFollowingCount();
            followed.decrementFollowersCount();
            follower.setFollowing(follower.getFollowing() - 1);
            followed.setFollowers(followed.getFollowers() - 1);
        }
    }

    // Method to check if a user is following another user
    public boolean isFollowing(User follower, User followed) {
        String query = "SELECT * FROM following WHERE followed_id = ? AND User_ID = ?";
        try (Connection conn = DatabaseController.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, followed.user_id(followed.getUsername()));
            pstmt.setInt(2, follower.user_id(follower.getUsername()));
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addFollowRecord(User follower, User followed) {
        String query = "INSERT INTO following (User_ID, followed_id) VALUES (?, ?)";
        try (Connection conn = DatabaseController.connect();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, follower.user_id(follower.getUsername()));
            pstmt.setInt(2, followed.user_id(followed.getUsername()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void removeFollowRecord(User follower, User followed) {
        String query = "DELETE FROM following WHERE User_ID = ? AND followed_id = ?";
        try (Connection conn = DatabaseController.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, follower.user_id(follower.getUsername()));
            pstmt.setInt(2, followed.user_id(followed.getUsername()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}