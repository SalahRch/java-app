package com.example.Modelcontrollers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//generate class for database connection
public class DatabaseController {
    private static Connection connection;
    private static Statement statement;

    public static Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/socialmedia", "root", "");
            statement = connection.createStatement();
            return connection;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void disconnect() throws SQLException {
        connection.close();
    }

    public static Statement getStatement() {
        return statement;
    }

}
