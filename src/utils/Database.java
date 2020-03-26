/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Dana Altier
 */

public class Database {
    
    private static final String DBNAME = "U03UBj";
    private static final String URL = "jdbc:mysql://52.206.157.109/U03UBj";
    private static final String USER = "U03UBj";
    private static final String PASSWORD = "53688083229";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    public static Connection conn;
    
    public Database() {
    
    }
    
    //Connect to DB
    public static void dbConnect() {
        
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to MySQL DB");
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found " + ex.getMessage());
        }
            
    }
    
    //Close DB connection
    public static void dbDisconnect() {
        
        try{
            conn.close();
            System.out.println("Disconnected from MySQL DB");
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        }
        
    }
    
    //Return DB connection
    public static Connection getDBConnection() {
        
        return conn;
        
    }
    
}
