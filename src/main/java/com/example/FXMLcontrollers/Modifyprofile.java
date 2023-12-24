package com.example.FXMLcontrollers;

import com.example.Exceptions.ValidationException;
import com.example.Modelcontrollers.UserController;
import com.example.Models.User;
import com.example.session.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

public class Modifyprofile {


    @FXML
    public ImageView Profilepic;
    @FXML
    public Label Username2;
    @FXML
    public TextField Name;
    @FXML
    public TextField Username;
    @FXML
    public TextField datebirth;
    @FXML
    public TextField PhoneNum;
    @FXML
    public TextField Email;
    @FXML
    public TextField Bio;

    public void initialize() {
        String username = com.example.session.UserSession.getUsername();
        Username2.setText(username);
        User user = User.userRetrieve(username);
        String profileURL = user.getProfilepic();
        if(profileURL !=null) {
            javafx.scene.image.Image image = new javafx.scene.image.Image(profileURL, true); // enable background loading
            image.progressProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal.doubleValue() == 1.0) {
                    Profilepic.setImage(image);
                    Circle clip = new Circle();
                    clip.setCenterX(Profilepic.getFitWidth() / 2);
                    clip.setCenterY(Profilepic.getFitHeight() / 2);
                    clip.setRadius(Math.min(Profilepic.getFitWidth(), Profilepic.getFitHeight()) / 2);
                    Profilepic.setClip(clip);
                }
            });
        }
        Name.setText(user.getFullname());
        Username.setText(user.getUsername());
        datebirth.setText(user.getBirthday());
        PhoneNum.setText(user.getPhoneNum());
        Email.setText(user.getEmail());
        if(user.getBio() != null) {
            Bio.setText(user.getBio());
        }
        else {
            Bio.setText("");
        }
    }
    @FXML
    public void editpfp() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            String destinationPath = "images" + selectedFile.getName();
            File destinationFile = new File(destinationPath);
            try {
                Files.copy(selectedFile.toPath(), destinationFile.toPath());
                String profilePictureUrl = "file:///" + destinationPath;
                User.updateProfilePicture(UserSession.getUsername(), profilePictureUrl);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File selection cancelled.");
        }
    }
    public void save() {
        String username = Username.getText();
        String fullname = Name.getText();
        String birthday = datebirth.getText();
        String phonenum = PhoneNum.getText();
        String email = Email.getText();
        String bio = Bio.getText();
        try {
            UserController userController = new UserController();
            userController.updateprofile(current_id(),username,fullname,birthday,phonenum,bio,email);
        } catch (SQLException | ValidationException throwables) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(throwables.getMessage());
            alert.showAndWait();
        }
    }
    public int current_id() throws SQLException {
        String username = UserSession.getUsername();
        User user = User.userRetrieve(username);
        int id = user.user_id(username);
        return id;
    }

    public void UserProfile() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(com.example.socialmediapp.HelloApplication.class.getResource("Userprofile.fxml"));
        javafx.scene.Scene scene = new javafx.scene.Scene(fxmlLoader.load(), 1315, 800);
        Stage stage1 = new Stage();
        stage1.setTitle("Social Media App");
        stage1.setScene(scene);
        stage1.show();
        Stage stage = (Stage) Username2.getScene().getWindow();
        stage.close();
    }

    public void Notifications() {
    }

}
