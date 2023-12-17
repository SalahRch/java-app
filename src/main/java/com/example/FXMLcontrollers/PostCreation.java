package com.example.FXMLcontrollers;

import com.example.Modelcontrollers.PostController;
import com.example.Models.User;
import com.example.session.UserSession;
import com.example.socialmediapp.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.plaf.ColorUIResource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PostCreation {
    @FXML
    public TextArea text;
    @FXML
    public ImageView documents;
    @FXML
    public ImageView images;
    @FXML
    public ImageView closeButton;
    public PostController postController= new PostController();
    private File selectedDocument;
    private User Currentuser;

    @FXML
    public void initialize() {
        closeButton.setOnMouseClicked(event -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
        Currentuser = User.userRetrieve(UserSession.getUsername());
    }
    @FXML
    public Image LoadImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            images.setImage(image);
        }
        return images.getImage();
    }
    @FXML
    public File LoadDocument(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Document Files", "*.pdf", "*.docx", "*.txt"));
        selectedDocument = fileChooser.showOpenDialog(null);
        return selectedDocument;
    }
    @FXML
    public void createPost() throws IOException {
        String postText = text.getText();
        Image postImage = images.getImage();
        String defaultIconUrl = HelloApplication.class.getResource("images/image-galery.png").toExternalForm();
        System.out.println(defaultIconUrl);
        System.out.println(postImage.getUrl());
        byte[] imageBytes = null;
        if(postImage != null && !postImage.getUrl().equals(defaultIconUrl)){
            imageBytes = Files.readAllBytes(new File(postImage.getUrl().substring(6)).toPath());
        }
        File postDocument = selectedDocument;
        byte[] documentBytes = null;
        if(postDocument != null) {
            documentBytes = Files.readAllBytes(postDocument.toPath());
        }
        postController.createPost(postText,Currentuser.user_id(Currentuser.getUsername()),documentBytes,imageBytes);
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
