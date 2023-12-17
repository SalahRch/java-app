package com.example.FXMLcontrollers;

import com.example.Modelcontrollers.NotificationController;
import com.example.Modelcontrollers.UserController;
import com.example.Models.User;
import com.example.session.UserSession;
import com.example.socialmediapp.HelloApplication;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {
    @FXML
    public ImageView profilepic;
    @FXML
    public Label Username;
    @FXML
    public TextField searchbar;
    @FXML
    public ListView<String> searchResults;
    @FXML
    public Hyperlink Profile;
    @FXML
    public Button notificationsButton;
    public NotificationController notificationController = new NotificationController();
    @FXML
    public ImageView notifbell;

    public void initialize() {
        String username = com.example.session.UserSession.getUsername();
        Username.setText(username);
        User user = User.userRetrieve(username);
        String profileURL = user.getProfilepic();
        if(profileURL !=null) {
            javafx.scene.image.Image image = new javafx.scene.image.Image(profileURL, true); // enable background loading
            image.progressProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal.doubleValue() == 1.0) { // when image has loaded
                    profilepic.setImage(image);
                    Circle clip = new Circle();
                    clip.setCenterX(profilepic.getFitWidth() / 2);
                    clip.setCenterY(profilepic.getFitHeight() / 2);
                    clip.setRadius(Math.min(profilepic.getFitWidth(), profilepic.getFitHeight()) / 2);
                    profilepic.setClip(clip);
                }
            });
        }
        searchResults.setVisible(false);
        searchResults.setOnMouseExited(event -> searchResults.setVisible(false));
        searchResults.setOnMouseClicked(event -> {
            String selectedUsername = searchResults.getSelectionModel().getSelectedItem();
            try {
                loadUserProfile(selectedUsername);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        if(notificationController.hasNoNotifs(User.userRetrieve(UserSession.getUsername()))){
            notifbell.setImage(new javafx.scene.image.Image("C:\\Users\\srouc\\Desktop\\bs\\battleship\\socialmediAPP\\src\\main\\resources\\com\\example\\socialmediapp\\images\\bell.png"));

        }
        else{
            notifbell.setImage(new javafx.scene.image.Image("C:\\Users\\srouc\\Desktop\\bs\\battleship\\socialmediAPP\\src\\main\\resources\\com\\example\\socialmediapp\\images\\bellnot.png"));
        }
    }
    private void loadUserProfile(String selectedUsername) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("OtherUserProfile.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1315, 800);
        OtherUserProfile otherUserProfileController = fxmlLoader.getController();
        User selectedUser = User.userRetrieve(selectedUsername);
        otherUserProfileController.initialize(selectedUser);
        System.out.println("user:"+selectedUser.getUsername());
        Stage stage1 = new Stage();
        stage1.setTitle("Social Media App - " + selectedUsername + "'s Profile");
        stage1.setScene(scene);
        stage1.show();
        Stage stage = (Stage) Profile.getScene().getWindow();
        stage.close();
    }

    public void search() throws SQLException {
        String search = searchbar.getText();
        UserController userController = new UserController();
        List <User> users = userController.search(search);
        List<String> usernames = users.stream().map(User::getUsername).collect(Collectors.toList());
        searchResults.getItems().clear();
        searchResults.setItems(FXCollections.observableArrayList(usernames));
        searchResults.setVisible(true);
    }


    public void Userprofile() throws IOException {
        //switch to user profile fxml
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Userprofile.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1315, 800);
        Stage stage1 = new Stage();
        stage1.setTitle("Social Media App");
        stage1.setScene(scene);
        stage1.show();
        Stage stage = (Stage) Profile.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void showNotifications() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("NotificationListView.fxml"));
            Node notifications = fxmlLoader.load();
            Scene currentScene = notificationsButton.getScene();
            currentScene.setRoot((Parent) notifications);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void openPostCreationWindow() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("PostCreation.fxml"));
            // Create a new stage and scene for the post creation window
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 524, 160);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void redirectChat() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 600);
        Stage stage = new Stage();
        stage.setTitle("ChatApp");
        stage.setScene(scene);
        stage.show();

    }
}
