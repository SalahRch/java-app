module com.example.socialmediapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires mysql.connector.java;
    requires java.desktop;

    opens com.example.socialmediapp to javafx.fxml;
    exports com.example.socialmediapp;
    exports com.example.Modelcontrollers;
    exports com.example.Models;
    exports com.example.FXMLcontrollers;
    exports com.example.chat;
    exports com.example.chat.model;
    exports com.example.Exceptions;
    opens com.example.FXMLcontrollers to javafx.fxml;
}