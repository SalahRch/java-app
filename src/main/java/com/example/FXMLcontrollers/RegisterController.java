package com.example.FXMLcontrollers;

import com.example.Exceptions.ValidationException;
import com.example.Modelcontrollers.UserController;
import com.example.socialmediapp.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {


    @FXML
    public javafx.scene.control.ComboBox<String> Departement;
    @FXML
    public Button Submit;
    @FXML
    public Button Reset;
    @FXML
    public DatePicker Birthday;
    @FXML
    public javafx.scene.control.TextField Username;
    @FXML
    public javafx.scene.control.TextField Email;
    @FXML
    public javafx.scene.control.TextField Password;
    @FXML
    public TextField FullName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Informatique",
                        "Data",
                        "Mécanique",
                        "GEER",
                        "Génie civil",
                        "Génie mécanique",
                        "GEE"
                        // Add more options as needed
                );
        Departement.setItems(options);
    }


    public void Register() {
        String username = null;
        String email = null;
        String password = null;
        String departement = null;
        String birthday = null;
        String fullname = null;
        try {
            username = this.Username.getText();
            email = this.Email.getText();
            password = this.Password.getText();
            departement = this.Departement.getValue();
            birthday = this.Birthday.getValue().toString();
            fullname = this.FullName.getText();
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        }

    if(username != null && email != null && password != null && departement != null && birthday != null && fullname != null) {
        UserController userController = new UserController();
        try {
            System.out.println(departement);
            userController.register(username, email, password, departement, birthday, fullname);
        } catch (ValidationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    }

    public void ResetALL() {

        this.Username.setText(null);
        this.Email.setText(null);
        this.Password.setText(null);
        this.Departement.setValue(null);
        this.Birthday.setValue(null);
        this.FullName.setText(null);

    }

    public void switchlogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 600);
        Stage stage1 = new Stage();
        stage1.setTitle("Social Media App");
        stage1.setScene(scene);
        stage1.show();
        Stage stage = (Stage) Submit.getScene().getWindow();
        stage.close();
    }
}
