package com.example.FXMLcontrollers;


import com.example.Exceptions.ValidationException;
import com.example.Modelcontrollers.UserController;
import com.example.Models.User;
import com.example.session.UserSession;
import com.example.socialmediapp.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public Button Loginbutton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public javafx.scene.control.TextField Username;
    @FXML
    public PasswordField Password;


    @FXML
    public void loginbutton() {
        try {
            String username = Username.getText();
            String password = Password.getText();
            UserController userController = new UserController();
            if (userController.isUserValid(username, password)) {
                User user = User.userRetrieve(username);
                String email = user.getEmail();
                UserSession.getInstance(username, email);
                switchview();
            }
        } catch (ValidationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void registerbutton() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 600);
        Stage stage1 = new Stage();
        stage1.setTitle("Social Media App");
        stage1.setScene(scene);
        stage1.show();
        Stage stage = (Stage) Loginbutton.getScene().getWindow();
        stage.close();
    }


    public void Forgotpw() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("forgotpassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 600);
        Stage stage1 = new Stage();
        stage1.setTitle("Social Media App");
        stage1.setScene(scene);
        stage1.show();
        Stage stage = (Stage) Loginbutton.getScene().getWindow();
        stage.close();
    }
    public void switchview() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1315, 800);
        Stage stage1 = new Stage();
        stage1.setTitle("Social Media App");
        stage1.setScene(scene);
        stage1.show();
        Stage stage = (Stage) Loginbutton.getScene().getWindow();
        stage.close();
    }
}
