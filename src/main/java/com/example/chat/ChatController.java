package com.example.chat;
import com.example.Models.User;
import com.example.chat.model.*;

import java.sql.Timestamp;

import com.example.socialmediapp.HelloApplication;
import com.mysql.cj.jdbc.Blob;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.chat.model.AppQuery.Me;

public class ChatController {
    public User userz = new User();

    public int id =userz.user_id(Me.getUsername());
    @FXML
    public  VBox chatVbox;
    @FXML
    public BorderPane borderPane;

    @FXML
    public HBox left_HBox;

    @FXML
    public VBox left_VBox;

    @FXML
    public ImageView enah_logo;

    @FXML
    public ImageView add_friend;

    @FXML
    public ImageView add_group;

    @FXML
    public ImageView go_back;

    @FXML
    public VBox right_VBox;
    @FXML
    public VBox friend_list;
    @FXML
    public HBox friends_box;
    @FXML
    public VBox searchicon_vbox;
    @FXML
    public HBox chat_username_box;
    @FXML
    public ImageView pdp_client1;

    @FXML
    public Label username_client;

    @FXML
    public Circle circle_enligne;

    @FXML
    public VBox right_VBox2;

    @FXML
    public HBox top_HBox;

    @FXML
    public ImageView client_pdp2;

    @FXML
    public Label username_client2;

    @FXML
    public ScrollPane chatScrollPane;



    @FXML
    public HBox client_box;

    @FXML
    public ImageView client_pdp3;

    @FXML
    public Label client_message;

    @FXML
    public Label date_message_client;

    @FXML
    public HBox server_box;

    @FXML
    public Label date_message_server;

    @FXML
    public Label client_message1;

    @FXML
    public ImageView pdp_server;

    @FXML
    public HBox bottom_HBox;

    @FXML
    public TextField message_text;
    @FXML
    public TextField search_friend;

    @FXML
    public Button sendbutton;
    @FXML
    public VBox add_utilisateur_vbox;

    public Socket clientSocket;
    public DataInputStream inputStream;
    public DataOutputStream outputStream;
    public Utilisateur currentChatUser;
    public Client activeClient; // Maintain reference to the active client
    public Utilisateur getCurrentChatUser() {
        return currentChatUser;
    }

    public void setCurrentChatUser(Utilisateur currentChatUser) {
        this.currentChatUser = currentChatUser;
    }

    @FXML
    public void initialize() {
    }


    @FXML
    public void switchToAddUtilisateur() {
        try {
            // Store the original background color
            String originalStyle = add_utilisateur_vbox.getStyle();

            // Set the new background color
            add_utilisateur_vbox.setStyle("-fx-background-color: #d3d3d3;");

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("add_Utilisateur.fxml"));
            Parent root = loader.load();

            // Create a new stage for the new FXML
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 360, 443)); // Set width and height as needed
            stage.setTitle("Add Utilisateur");

            // Set a listener for when the stage is closed
            stage.setOnHidden(event -> {
                // Revert to the original background color when the stage is closed
                add_utilisateur_vbox.setStyle(originalStyle);
            });

            // Show the new stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void searchfriend() {

        String style=searchicon_vbox.getStyle();
        searchicon_vbox.setStyle("-fx-background-color: #d3d3d3 ;");
        // Create a timeline for reverting the color after 1 second
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), e -> {

            searchicon_vbox.setStyle("-fx-background-color: style;"); // Revert to the original color
        }));
        timeline.play();

        int serverPort = id+1000; // Choose a suitable port number
        System.out.println(serverPort);
        Server server = new Server(serverPort,this);
        Thread serverThread = new Thread(server);
        serverThread.start();
        String searchText = search_friend.getText();
        List<Utilisateur> users = AppQuery_main.searchUtilisateur(searchText);
        friend_list.getChildren().clear();

        for (Utilisateur user : users) {
            HBox userBox = createUserBox(user);

            Pane bare = new Pane();
            bare.setPrefWidth(250);
            bare.setPrefHeight(2);
            bare.setStyle("-fx-background-color:  #1d2951;");
            bare.setVisible(true);

            friend_list.getChildren().addAll(userBox, bare);
        }
    }

    public HBox createUserBox(Utilisateur user) {
        HBox userBox = new HBox();

        ImageView pdpImageView=null;
        if(user.getPdp()==null){
            pdpImageView = new ImageView("C:\\Users\\srouc\\Desktop\\bs\\battleship\\socialmediAPP\\src\\main\\resources\\com\\example\\socialmediapp\\images\\person.jpeg");

        }else {
            pdpImageView = createPdpImageView(user.getPdp());
        } // Assuming getAvatar returns the image path
        pdpImageView.setFitHeight(40);
        pdpImageView.setFitWidth(40);

        Label usernameLabel = new Label(user.getUsername());



        userBox.getChildren().addAll( pdpImageView , usernameLabel );

        // Set properties similar to the FXML-defined HBox
        userBox.setPrefHeight(40);
        userBox.setPrefWidth(263);
        userBox.setTranslateX(-12);
        pdpImageView.setTranslateX(15);
        pdpImageView.setTranslateY(1);

        usernameLabel.setPrefHeight(24);
        usernameLabel.setPrefWidth(148);
        usernameLabel.setTranslateX(28);
        usernameLabel.setTranslateY(9);

        User otheruser= new User();
        int other_id =otheruser.user_id(user.getUsername());


        userBox.setOnMouseClicked(event -> {
            // Establish connection when the user box is clicked
            connectToServer(id, other_id);
            chatBox(user);
            String originalStyle = userBox.getStyle();
            userBox.setStyle("-fx-background-color: #d3d3d3;"); // Change background color when clicked

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.7), e -> {
                        userBox.setStyle(originalStyle); // Revert to the original background color after 1 second
                    })
            );
            timeline.play();
        }); // Set event on addButton
        sendbutton.setOnMouseClicked(event -> sendMessage());
        return userBox;
    }

    public ImageView createPdpImageView(Blob blob) {
        try {
            byte[] imageData = blob.getBytes(1, (int) blob.length());
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
            Image image = new Image(inputStream);
            return new ImageView(image);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
            return new ImageView(); // Return a default ImageView in case of an error
        }
    }
    public void chatBox(Utilisateur user) {
        User otheruser= new User();
        int other_id =otheruser.user_id(user.getUsername());
        currentChatUser = user;
        int yourUserId = id; // Retrieve the ID of the logged-in user
        int otherUserId = other_id; //
        List<Message> messages = AppQuery_main.getMessagesBetweenUsers(yourUserId, otherUserId);

        // Clear the chatVbox to prepare for new messages
        chatVbox.getChildren().clear();
        // Set the username and profile picture in the chat_username_box
        ImageView pdpImageView=null;
        if(user.getPdp()==null){
            pdpImageView = new ImageView("C:\\Users\\srouc\\Desktop\\bs\\battleship\\socialmediAPP\\src\\main\\resources\\com\\example\\socialmediapp\\images\\person.jpeg");

        }else {
            pdpImageView = createPdpImageView(user.getPdp());
        }
        pdpImageView.setFitHeight(53);
        pdpImageView.setFitWidth(34);

        chat_username_box.setStyle("-fx-background-color: #d3d3d3 ;   -fx-border-radius: 10px;");

        // Apply styles to the profile picture
        pdpImageView.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #FFFFFF; -fx-border-radius: 5px; -fx-padding: 5px;");

        // Additional styling for the Username_client2 label
        Label usernameLabel = new Label(user.getUsername());
        usernameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #000000;");
        usernameLabel.setTranslateX(10);

        // Clear the chat_username_box before adding new content
        chat_username_box.setPrefHeight(70);
        chat_username_box.setPrefWidth(434);
        chat_username_box.getChildren().clear();
        chat_username_box.getChildren().addAll(pdpImageView, usernameLabel);
        for (Message message : messages) {
            if (message.getSenderId() == yourUserId) {
                // Message sent by the logged-in user
                displaySentMessage(message);
            } else {
                // Message received from the other user
                displayReceivedMessage(message);
            }


        }
//        Platform.runLater(() -> chatScrollPane.setVvalue(1.0));
    }

    public void connectToServer(int yourUserId, int otherUserId) {
        // Close the existing connection if it's not null

        if (activeClient != null ) {
            activeClient.closeClient();
        }
        Client client = new Client("localhost", otherUserId+1000,this); // Replace with the appropriate constructor parameters
        Thread clientThread = new Thread(client);
        clientThread.start();
        // Assign the new client as the active client
        activeClient = client;
    }

    public void displaySentMessage(Message message) {
        HBox sentMessage = createMessageContainer(message.getMessageContent(), message.getTimestamp(), true);
        chatVbox.getChildren().add(sentMessage);
        chatScrollPane.setVvalue(1.0);
    }

    public void displayReceivedMessage(Message message) {
        HBox receivedMessage = createMessageContainer(message.getMessageContent(), message.getTimestamp(), false);
        chatVbox.getChildren().add(receivedMessage);

        chatScrollPane.setVvalue(1.0);
    }


    public static HBox createMessageContainer(String content, Timestamp timestamp, boolean sentByCurrentUser) {
        HBox messageBox = new HBox();
        Label messageLabel = new Label(content);
        LocalDateTime localDateTime = timestamp.toLocalDateTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM");
        String formattedDateTime = localDateTime.format(formatter);
        Label timestampLabel = new Label(formattedDateTime) ; // Use the timestamp

        messageLabel.setWrapText(true);
        messageLabel.setPadding(new Insets(10)); // Add padding to the message label

        // Customize the appearance of the message box and labels based on sender
        if (sentByCurrentUser) {
            messageBox.setAlignment(Pos.CENTER_RIGHT);
            messageLabel.setTranslateX(-5);
            messageLabel.setStyle("-fx-background-color: #DCF8C6; -fx-background-radius: 10; -fx-padding: 8px;");
            timestampLabel.setStyle("-fx-font-size:8px; -fx-font-weight: bold; -fx-text-fill: #999999; -fx-padding: 2px 4px; -fx-background-color: #f2f2f2; -fx-background-radius: 4px;");
        } else {
            messageBox.setAlignment(Pos.CENTER_LEFT);
            messageLabel.setTranslateX(5);
            messageLabel.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-padding: 8px;");
            timestampLabel.setStyle("-fx-font-size: 8px; -fx-font-weight: bold; -fx-text-fill: #999999; -fx-padding: 2px 4px; -fx-background-color: #f2f2f2; -fx-background-radius: 4px;");
        }

        // Add message content and timestamp labels to the messageBox
        VBox messageContent = new VBox(messageLabel, timestampLabel);
        messageContent.setSpacing(5); // Adjust the spacing between message content and timestamp
        messageBox.getChildren().add(messageContent);

        return messageBox;
    }
    /////////////////////////////////////////////////////////
    @FXML
    public void sendMessage() {
        String messageText = message_text.getText(); // Get text from the messagetext TextField
        message_text.clear();


        // Get the IDs of the sender and receiver
        int yourUserId = id;
        int otherUserId = currentChatUser.getId();

        // Save the message to the database
        boolean messageSent = AppQuery_main.saveMessageToDatabase(yourUserId, otherUserId, messageText);

        if (currentChatUser!=null) {

            Message msg=new Message(messageText);
            displaySentMessage(msg);
            if (activeClient != null  ) {
                activeClient.sendMessage(messageText);
            }}
        // Create unique ports for each user pair or utilize a different strategy to differentiate communication

    }



}
