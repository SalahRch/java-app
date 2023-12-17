package com.example.FXMLcontrollers;

import com.example.Exceptions.ValidationException;
import com.example.Modelcontrollers.UserController;
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

import java.io.IOException;
import java.sql.SQLException;

public class ForgotpasswordController2 {
    @FXML
    public TextField Password;
    @FXML
    public PasswordField Confirmedpw;
    @FXML
    public Button Confirmbutton;

    public void Confirm() throws IOException {
        String password = Password.getText();
        String confirmedpw = Confirmedpw.getText();
        UserSession userSession = UserSession.getInstance(null, null);
        UserController userController = new UserController();
        try {
            userController.updateUser(userSession.getUsername(), password, confirmedpw);
            userSession.cleanUserSession();
            switchview();
        } catch (ValidationException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void switchview() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 600);
        Stage stage1 = new Stage();
        stage1.setTitle("Social Media App");
        stage1.setScene(scene);
        stage1.show();
        Stage stage = (Stage) Confirmbutton.getScene().getWindow();
        stage.close();
    }
}
