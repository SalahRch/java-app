package com.example.FXMLcontrollers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PostControllers {
    @FXML
    public HBox likeContainer;
    @FXML
    public ImageView imgReaction;
    @FXML
    public Label reactionName;
    @FXML
    public ImageView imgProfile;
    @FXML
    public Label username;
    @FXML
    public Label date;
    @FXML
    public Label caption;
    @FXML
    public ImageView imgPost;
    @FXML
    public Label nbReactions;
    @FXML
    public Label nbComments;
    @FXML
    public Label nbShares;
    @FXML
    public HBox reactionsContainer;
    @FXML
    public ImageView imgLike;
    @FXML
    public ImageView imgLove;
    @FXML
    public ImageView imgCare;
    @FXML
    public ImageView imgHaha;
    @FXML
    public ImageView imgWow;
    @FXML
    public ImageView imgSad;
    @FXML
    public VBox vbox;


    @FXML
    public void initialize() {
        imgPost.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("Image clicked"); // Debugging line
            Image image = imgPost.getImage();
            if (image != null) {
                ImageView imageView = new ImageView(image);
                StackPane root = new StackPane(imageView);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }
            event.consume();
        });
    }

    public void onReactionImgPressed() {
    }

    public void onLikeContainerMouseReleased() {
    }

    public void onLikeContainerPressed() {
    }
}
