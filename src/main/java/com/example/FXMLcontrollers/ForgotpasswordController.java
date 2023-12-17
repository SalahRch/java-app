package com.example.FXMLcontrollers;

import com.example.Exceptions.ValidationException;
import com.example.Modelcontrollers.UserController;
import com.example.Models.User;
import com.example.session.UserSession;
import com.example.socialmediapp.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.SQLException;

public class ForgotpasswordController {
    @FXML
    public TextField Username;
    @FXML
    public TextField Email;
    @FXML
    public Button continuebutton;


    public void Continue() throws IOException {
        String username = Username.getText();
        String email = Email.getText();
        try{
            UserController userController = new UserController();
            userController.Emailcheck(username,email);
            UserSession.getInstance(username, email);
            switchview();
        } catch (ValidationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void switchview() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("forgotpassword2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 600);
        Stage stage1 = new Stage();
        stage1.setTitle("Social Media App");
        stage1.setScene(scene);
        stage1.show();
        Stage stage = (Stage) continuebutton.getScene().getWindow();
        stage.close();
    }

    public void loginpage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 600);
        Stage stage1 = new Stage();
        stage1.setTitle("Social Media App");
        stage1.setScene(scene);
        stage1.show();
        Stage stage = (Stage) continuebutton.getScene().getWindow();
        stage.close();
    }

}
