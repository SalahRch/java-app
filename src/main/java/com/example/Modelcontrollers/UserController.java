package com.example.Modelcontrollers;

import com.example.Exceptions.ValidationException;
import com.example.Models.User;
import com.example.session.UserSession;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.Models.User.encryptPassword;
import static java.util.Arrays.stream;

public class UserController {

    public static boolean isValiduser(String username) throws SQLException {
        User user = User.allUsers().stream()
                .filter(u -> u.getUsername()
                .equals(username))
                .findFirst()
                .orElse(null);
        return user != null;
    }
    public void Emailcheck(String username, String email) throws SQLException, ValidationException{
        if(!isValiduser(username)){
            throw new ValidationException("User don't exist !");
        }
        if(!isValidmail(email)){
            throw new ValidationException("Email don't exist !");
        }
    }

    private boolean isValidmail(String email) throws SQLException {
        User user = User.allUsers().stream()
                .filter(u -> u.getEmail()
                .equals(email))
                .findFirst()
                .orElse(null);
        return user != null;
    }

    public boolean isUserValid(String username,String password) throws SQLException, ValidationException {
        if(username == null || username.trim().isEmpty()){
            throw new ValidationException("Username is empty");
        }
        if(password == null || password.trim().isEmpty()){
            throw new ValidationException("Password is empty");
        }
        User user = User.allUsers().stream()
                .filter(u -> u.getUsername()
                .equals(username))
                .findFirst()
                .orElse(null);
        if(user == null){
            throw  new ValidationException("User not found");
        }
        if(!user.getPassword().equals(encryptPassword(password))){
            throw new ValidationException("Wrong password");
        }
        return true;
    }
    public void register(String username,String email,String password,String departement,String birthdate,String fullname) throws SQLException, ValidationException {

       if(isValiduser(username)){
           throw new ValidationException("User already exists");
       }
        if(isValidmail(email)){
            throw new ValidationException("Email already used");
        }
       //validate the email it must end with @etu.uae.ac.ma
         if(!email.endsWith("@etu.uae.ac.ma") ){
              throw new ValidationException("Email must end with @etu.uae.ac.ma");
         }
       //validate the password it must be at least 8 characters
       if(password.length() < 8) {
                throw new ValidationException("Password must be at least 8 characters");
       }

       User newUser = new User(username,password);
       newUser.setEmail(email);
       newUser.setDepartment(departement);
       newUser.setBirthday(birthdate);
       newUser.setFullname(fullname);
        try {
            newUser.insertUser();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }

    }

    public void updateUser(String username, String password, String confirmedpw) throws ValidationException, SQLException {
        if(!password.equals(confirmedpw)){
            throw new ValidationException("Password and confirmed password don't match");
        }
        if(password.length() < 8) {
            throw new ValidationException("Password must be at least 8 characters");
        }
        if(!isValiduser(username)){
            throw new ValidationException("User don't exist !");
        }
        User.updateUser(username,password);

    }

    public List<User> search(String username) throws SQLException {
        if (username.isEmpty()) {
            return new ArrayList<>();
        }

        //return list of users that match the username
        List<User> users = User.allUsers();
        return users.stream()
                .filter(u -> u.getUsername().contains(username))
                .toList();
    }
    public void updateprofile(int id,String username,String fullname,String birthday,String phonenumber,String bio,String email) throws SQLException, ValidationException {
        if(username == null || username.trim().isEmpty()){
            throw new ValidationException("Username is empty");
        }
        if(fullname == null || fullname.trim().isEmpty()){
            throw new ValidationException("Full name is empty");
        }
        if(birthday == null || birthday.trim().isEmpty()){
            throw new ValidationException("Birthday is empty");
        }
        if(phonenumber == null || phonenumber.trim().isEmpty()){
            throw new ValidationException("Phone number is empty");
        }
        if(email == null || email.trim().isEmpty()){
            throw new ValidationException("Email is empty");
        }
        if(isValiduser(username) && !username.equals(UserSession.getUsername())){
            throw new ValidationException("Username taken !");
        }
        if(isValidmail(email) && !email.equals(UserSession.getEmail())){
            throw new ValidationException("Email already used");
        }
        if(!email.endsWith("@etu.uae.ac.ma") ){
            throw new ValidationException("Email must end with @etu.uae.ac.ma");
        }
        if(phonenumber.length() != 10){
            throw new ValidationException("Phone number must have 10 digits");
        }
        User user = User.userRetrieve(username);
        user.setFullname(fullname);
        user.setBirthday(birthday);
        user.setPhoneNum(phonenumber);
        user.setBio(bio);
        user.setEmail(email);
        user.updateProfile(id);
    }

}
