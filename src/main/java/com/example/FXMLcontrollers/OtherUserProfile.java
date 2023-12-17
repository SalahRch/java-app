package com.example.FXMLcontrollers;

import com.example.Modelcontrollers.FollowController;
import com.example.Modelcontrollers.NotificationController;
import com.example.Models.User;
import com.example.session.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class OtherUserProfile {
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
    public Button followbutton;
    public User profileUser;
    private NotificationController notificationController;

    @FXML
    public void initialize(User user){
        notificationController = new NotificationController();
        this.profileUser = user;
        String username = profileUser.getUsername();
        Username.setText(username);
        updateProfileUI();

        if(UserSession.getUsername().equals(username)){
            followbutton.setVisible(false);
        }
        else{
            FollowController followController = new FollowController();
            if(followController.isFollowing(User.userRetrieve(UserSession.getUsername()),profileUser)){
                setUnfollowButton(followController);
            }
            else{
                setFollowButton(followController);
            }
        }
    }

    private void updateProfileUI() {
        numfollowers.setText(String.valueOf(profileUser.getFollowers()));
        numfollowing.setText(String.valueOf(profileUser.getFollowing()));
        numposts.setText(String.valueOf(profileUser.getPosts()));
        String profileURL = profileUser.getProfilepic();
        if(profileURL !=null) {
            profilepic.setImage(new javafx.scene.image.Image("file:///" + profileURL));
        }
    }

    private void setFollowButton(FollowController followController) {
        followbutton.setText("Follow");
        followbutton.setOnAction(event -> {
            notificationController.createNotification("follow",User.userRetrieve(UserSession.getUsername()),profileUser,UserSession.getUsername()+" has requested to follow you !");
            followbutton.setText("Requested");
            followbutton.setDisable(true);
        });
    }

    private void setUnfollowButton(FollowController followController) {
        followbutton.setText("Unfollow");
        followbutton.setOnAction(event -> {
            try {
                followController.unfollowUser(User.userRetrieve(UserSession.getUsername()),profileUser);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            followbutton.setText("Follow");
            updateProfileUI();
            setFollowButton(followController);
        });
    }
}




