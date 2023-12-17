package com.example.FXMLcontrollers;

import com.example.Modelcontrollers.FollowController;
import com.example.Modelcontrollers.NotificationController;
import com.example.Models.Notification;
import com.example.Models.User;
import com.example.session.UserSession;
import com.example.socialmediapp.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationListController {
    @FXML
    private ListView<Notification> followrequests;

    @FXML
    private ListView<Notification> interactions;

    private FollowController followController = new FollowController();
    private NotificationController notificationController = new NotificationController();

    @FXML
    public void initialize() {
        updateNotificationLists();
    }

    private void updateNotificationLists() {
        NotificationController notificationController = new NotificationController();
        List<Notification> notifications = notificationController.getNotifications(User.userRetrieve(UserSession.getUsername()));

        List<Notification> followRequests = notifications.stream()
                .filter(n -> n.getType().equals("follow"))
                .collect(Collectors.toList());

        List<Notification> interactionNotifications = notifications.stream()
                .filter(n -> !n.getType().equals("follow"))
                .collect(Collectors.toList());

        populateListView(followrequests, followRequests);
        populateListView(interactions, interactionNotifications);
    }

    private void populateListView(ListView<Notification> listView, List<Notification> notifications) {
        ObservableList<Notification> observableList = FXCollections.observableArrayList(notifications);
        listView.setItems(observableList);
        listView.setCellFactory(param -> new ListCell<>() {
            private HBox content = new HBox();
            private Text mainText = new Text();
            private Button acceptButton = new Button("Accept");
            private Button refuseButton = new Button("Refuse");

            @Override
            protected void updateItem(Notification item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    mainText.setText(item.getType() + ": " + item.getContent());
                    content.getChildren().clear();
                    content.getChildren().add(mainText);
                    if (item.getType().equals("follow")) {
                        acceptButton.setOnAction(event -> {
                            followController.followUser(item.getSender(),item.getReceiver());
                            mainText.setText("You are now being followed by " + item.getSender().getUsername());
                            acceptButton.setVisible(false);
                            refuseButton.setVisible(false);
                            notificationController.deleteNotification(item);
                            setDisable(true);
                        });
                        refuseButton.setOnAction(event -> {
                            mainText.setText("You have refused the request of " + item.getSender().getUsername());
                            acceptButton.setVisible(false);
                            refuseButton.setVisible(false);
                            notificationController.deleteNotification(item);
                            setDisable(true);
                        });
                        content.getChildren().addAll(acceptButton, refuseButton);
                    }
                    setGraphic(content);
                }
            }
        });
    }

    public void goback() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Dashboard.fxml"));
        Stage stage1 = new Stage();
        try {
            stage1.setScene(new javafx.scene.Scene(fxmlLoader.load(), 1315, 800));
            stage1.setTitle("Social Media App");
            stage1.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) followrequests.getScene().getWindow();
        stage.close();
    }
}