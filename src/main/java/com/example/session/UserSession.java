package com.example.session;

public class UserSession {

    private static UserSession instance;

    private static String username;
    private static String email;

    private UserSession(String username, String email) {
        UserSession.username = username;
        this.email = email;
    }

    public static UserSession getInstance(String username, String email) {
        if(instance == null) {
            instance = new UserSession(username, email);
        }
        return instance;
    }

    public static String getUsername() {
        return username;
    }

    public static String getEmail() {
        return email;
    }

    public void cleanUserSession() {
        username = "";
        email = "";
        instance = null;
    }

}
