/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import utils.Database;
import utils.Logger;

/**
 *
 * @author Dana Altier
 */

public class User {
    
    private int userId;
    private String username;
    private String password;
    
    public static User currentUser;
    
    public User() {
    
    }
    
    public User(int userId, String username, String password) {
        
        this.userId = userId;
        this.username = username;
        this.password = password;
        
    }

    //Getters and setters for User
    
    public int getUserId() {
        
        return userId;
        
    }

    public void setUserId(int userId) {
        
        this.userId = userId;
        
    }

    public String getUsername() {
        
        return username;
        
    }

    public void setUsername(String username) {
        
        this.username = username;
        
    }

    public String getPassword() {
        
        return password;
        
    }

    public void setPassword(String password) {
        
        this.password = password;
        
    }
   
    //Checks user credentials against the DB and logs the result to log.txt
    
    public static Boolean login(String username, String password) {
        
        try {
            Statement statement = Database.getDBConnection().createStatement();
            String query = "SELECT * FROM user WHERE userName='" + username + "' AND password='" + password + "'";
            ResultSet results = statement.executeQuery(query);
            if(results.next()) {
                currentUser = new User();
                currentUser.setUsername(results.getString("userName"));
                statement.close();
                Logger.createLog(username, true);
                return true;
            } else {
                Logger.createLog(username, false);
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            return false;
        }
        
    }
    
}