package com.example.Models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Modelcontrollers.DatabaseController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class User {

       private IntegerProperty followers;
        private IntegerProperty following;
        private String username;
        private String password;
        private int age;
        private String email;
        private String department;
        private String major;
        private String Fullname;
        private String bio;
        private String profilePic;
        private String location;
        private String birthday;
        private int posts;
        private String phoneNum;

     public User() {
        followers = new SimpleIntegerProperty();
        following = new SimpleIntegerProperty();
      }

      public User(String username, String password) {
            this();
            this.username = username;
            this.password = encryptPassword(password);
        }
        public User(String username, String password, boolean isEncrypted){
            this();
            this.username = username;
            this.password = isEncrypted ? password : encryptPassword(password);
        }

    public static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertUser() throws SQLException {
            com.example.Modelcontrollers.DatabaseController.connect();
            String query = "INSERT INTO users (Username,Password,Email,Department,Full_name,Date_of_Birth) VALUES ('"+this.username+"','"+this.password+"','"+this.email+"','"+this.department+"','"+this.Fullname+"','"+this.birthday+"' )";
            DatabaseController.getStatement().executeUpdate(query);
            com.example.Modelcontrollers.DatabaseController.disconnect();
    }
    public static List<User> allUsers() throws SQLException {
        DatabaseController.connect();
        String query = "SELECT * FROM users";
        ResultSet rs = DatabaseController.getStatement().executeQuery(query);

        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User(rs.getString("Username"), rs.getString("Password"),true);
            user.setEmail(rs.getString("Email"));
            users.add(user);
        }
        DatabaseController.disconnect();
        return users;
    }

    public static void updateUser(String username,String password) throws SQLException {
              DatabaseController.connect();
              password = encryptPassword(password);
              String query = "UPDATE users SET Password = '"+password+"' WHERE Username = '"+username+"'";
              DatabaseController.getStatement().executeUpdate(query);
              DatabaseController.disconnect();
    }
    public static User userRetrieve(String username){
        try {
            DatabaseController.connect();
            String query = "SELECT * FROM users WHERE Username = '"+username+"'";
            ResultSet rs = DatabaseController.getStatement().executeQuery(query);
            if (rs.next()) {
                User user = new User(rs.getString("Username"), rs.getString("Password"),true);
                user.setEmail(rs.getString("Email"));
                user.setDepartment(rs.getString("Department"));
                user.setFullname(rs.getString("Full_name"));
                user.setBirthday(rs.getString("Date_of_Birth"));
                user.setBio(rs.getString("Bio_Summary"));
                if(rs.getString("Profile_Picture_URL") != null) {
                    user.setProfilePic(rs.getString("Profile_Picture_URL"));
                }
                user.setPhoneNum(rs.getString("Phone_number"));
                user.setFollowers(rs.getInt("Number_of_Followers"));
                user.setFollowing(rs.getInt("Number_of_Following"));
                DatabaseController.disconnect();
                return user;
            } else {
                throw new RuntimeException("No user found with username: " + username);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static User getUserbyID(int ID){
        try {
            DatabaseController.connect();
            String query = "SELECT * FROM users WHERE User_ID = '"+ID+"'";
            ResultSet rs = DatabaseController.getStatement().executeQuery(query);
            if (rs.next()) {
                User user = new User(rs.getString("Username"), rs.getString("Password"),true);
                user.setEmail(rs.getString("Email"));
                user.setDepartment(rs.getString("Department"));
                user.setFullname(rs.getString("Full_name"));
                user.setBirthday(rs.getString("Date_of_Birth"));
                user.setBio(rs.getString("Bio_Summary"));
                if(rs.getString("Profile_Picture_URL") != null) {
                    user.setProfilePic(rs.getString("Profile_Picture_URL"));
                }
                user.setPhoneNum(rs.getString("Phone_number"));
                user.setFollowers(rs.getInt("Number_of_Followers"));
                user.setFollowing(rs.getInt("Number_of_Following"));
                DatabaseController.disconnect();
                return user;
            } else {
                throw new RuntimeException("No user found with username:");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public String getUsername() {
            return username;
        }

    public void setUsername(String username) {
            this.username = username;
        }

    public String getPassword() {
            return password;
        }

    public void setPassword(String password) {
            this.password = encryptPassword(password);
        }

    public String getEmail() {
            return email;
        }

    public void setEmail(String email) {
            this.email = email;
        }

    public String getFullname() {
            return Fullname;
        }

    public void setFullname(String Fullname) {
            this.Fullname = Fullname;
        }
    public String getBio() {
            return bio;
        }

    public void setBio(String bio) {
            this.bio = bio;
        }

    public String getProfilePic() {
            return profilePic;
        }

    public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

    public String getBirthday() {
            return birthday;
        }

    public void setBirthday(String birthday) {
            this.birthday = birthday;
        }
    public String getDepartment() {
            return department;
    }
    public void setDepartment(String department) {
            this.department = department;
    }
    public String getProfilepic() {return profilePic;}

    public int getFollowers() {
        return followers.get();
    }

    public IntegerProperty followersProperty() {
        return followers;
    }

    public void setFollowers(int followers) {

         this.followers= new SimpleIntegerProperty(followers);
    }

    public int getFollowing() {
        return following.get();
    }

    public IntegerProperty followingProperty() {
        return following;
    }

    public void setFollowing(int following) {

         this.following= new SimpleIntegerProperty(following);
    }
    public int getPosts() {
            return posts;
    }

    public String getPhoneNum() {
            return phoneNum;
    }

    public void setPhoneNum(String text) {
            this.phoneNum = text;
    }

    public void userUpdate() {
        try {
            DatabaseController.connect();
            String query = "UPDATE users SET Full_name = '"+this.Fullname+"', Username = '"+this.username+"', Date_of_Birth = '"+this.birthday+"', Email = '"+this.email+"', Phone_number = '"+this.phoneNum+"', Bio_Summary = '"+this.bio+"' WHERE Username = '"+this.username+"'";
            DatabaseController.getStatement().executeUpdate(query);
            DatabaseController.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<User> getFollowers(User user) {
        List<User> followers = new ArrayList<>();
        String query = "SELECT User_ID FROM following WHERE followed_id = ?";
        try (Connection conn = DatabaseController.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, user.user_id(user.getUsername()));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Get the User_ID of the follower
                    int followerId = rs.getInt("User_ID");
                    // Retrieve the User object for the follower
                    User follower = User.userRetrieveById(followerId);
                    // Add the follower to the list of followers
                    followers.add(follower);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return followers;
    }
    public static void updateProfilePicture(String username, String profilePictureUrl) throws SQLException {
        String query = "UPDATE users SET Profile_Picture_URL = ? WHERE username = ?";
        try (Connection conn = DatabaseController.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, profilePictureUrl);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        }
    }

    private static User userRetrieveById(int followerId) {
        try {
            DatabaseController.connect();
            String query = "SELECT * FROM users WHERE User_ID = '"+followerId+"'";
            ResultSet rs = DatabaseController.getStatement().executeQuery(query);
            if (rs.next()) {
                User user = new User(rs.getString("Username"), rs.getString("Password"),true);
                user.setEmail(rs.getString("Email"));
                user.setDepartment(rs.getString("Department"));
                user.setFullname(rs.getString("Full_name"));
                user.setBirthday(rs.getString("Date_of_Birth"));
                user.setBio(rs.getString("Bio_Summary"));
                if(rs.getString("Profile_Picture_URL") != null) {
                    user.setProfilePic(rs.getString("Profile_Picture_URL"));
                }
                user.setPhoneNum(rs.getString("Phone_number"));
                DatabaseController.disconnect();
                return user;
            } else {
                throw new RuntimeException("No user found with id: " + followerId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int user_id(String username){
        try {
            DatabaseController.connect();
            String query = "SELECT * FROM users WHERE Username = '"+username+"'";
            ResultSet rs = DatabaseController.getStatement().executeQuery(query);
            if (rs.next()) {
                int id = rs.getInt("User_ID");
                DatabaseController.disconnect();
                return id;
            } else {
                throw new RuntimeException("No user found with username: " + username);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProfile(int user_id) {
        try {
            DatabaseController.connect();
            String query = "UPDATE users SET Full_name = '"+this.Fullname+"', Username = '"+this.username+"', Date_of_Birth = '"+this.birthday+"', Email = '"+this.email+"', Phone_number = '"+this.phoneNum+"', Bio_Summary = '"+this.bio+"' WHERE User_ID = '"+user_id+"'";
            DatabaseController.getStatement().executeUpdate(query);
            DatabaseController.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void incrementFollowingCount() {
        try {
            Connection conn = DatabaseController.connect();
            String query = "UPDATE users SET Number_of_Following = Number_of_Following + 1 WHERE Username = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.username);
            pstmt.executeUpdate();
            DatabaseController.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void incrementFollowersCount() {
        try {
            Connection conn = DatabaseController.connect();
            String query = "UPDATE users SET Number_of_Followers = Number_of_Followers + 1 WHERE Username = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.username);
            pstmt.executeUpdate();
            DatabaseController.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void decrementFollowingCount() {
        try {
            Connection conn = DatabaseController.connect();
            String query = "UPDATE users SET Number_of_Following = Number_of_Following - 1 WHERE Username = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.username);
            pstmt.executeUpdate();
            DatabaseController.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void decrementFollowersCount() {
        try {
            Connection conn = DatabaseController.connect();
            String query = "UPDATE users SET Number_of_Followers = Number_of_Followers - 1 WHERE Username = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.username);
            pstmt.executeUpdate();
            DatabaseController.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}


