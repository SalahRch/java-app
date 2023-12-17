package com.example.FXMLcontrollers;

import com.example.Modelcontrollers.PostController;
import com.example.Models.Post;
import com.example.Models.User;
import com.example.socialmediapp.HelloApplication;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class Userprofile {
    @FXML
    public Text numfollowers;
    @FXML
    public Text numposts;
    @FXML
    public Text numfollowing;
    @FXML
    public Text Username;
    @FXML
    public ImageView profilepic;
    @FXML
    public ListView<Post> postListView;


    private class PostListCell extends ListCell<Post> {
        @Override
        protected void updateItem(Post post, boolean empty) {
            super.updateItem(post, empty);
            if (post != null) {
                try {
                    // Load the post.fxml file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/socialmediapp/post.fxml"));
                    VBox postBox = loader.load();
                    User user = User.getUserbyID(post.getUserId());
                    PostControllers controller = loader.getController();
                    controller.username.setText(user.getUsername());
                    controller.imgProfile.setImage(new Image(user.getProfilepic()));
                    controller.date.setText(post.getTimestamp());
                    controller.caption.setText(post.getContent());
                    byte[] imageData = post.getImage();
                    if (imageData != null) {
                        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                        Image image = new Image(bis);
                        controller.imgPost.setImage(image);
                    }else {
                        controller.imgPost.setFitHeight(0);
                        controller.vbox.setPrefHeight(300);
                    }
                    setGraphic(postBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                setGraphic(null);
            }
        }
    }
    @FXML
    public void initialize() {
        postListView.setSelectionModel(null);
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
       numfollowers.setText(String.valueOf(user.getFollowers()));
       numfollowing.setText(String.valueOf(user.getFollowing()));
       numposts.setText(String.valueOf(user.getPosts()));
       PostController postController = new PostController();
       List<Post> posts = postController.posts(user.user_id(user.getUsername()));
       postListView.setItems(FXCollections.observableArrayList(posts));
       postListView.setCellFactory(new Callback<ListView<Post>, ListCell<Post>>() {
            @Override
            public ListCell<Post> call(ListView<Post> param) {
                return new PostListCell();
            }
        });

    }
    public void Modifyprofile() throws IOException {
        //switch to Profile_Settings.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Profile_Settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1220, 740);
        Stage stage1 = new Stage();
        stage1.setTitle("Social Media App");
        stage1.setScene(scene);
        stage1.show();
        Stage stage = (Stage) profilepic.getScene().getWindow();
        stage.close();

    }


    }
