/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

/**
 *
 * @author Dana Altier
 */

public class Logger {
    
    private static final String FILENAME = "log.txt";
    
    public Logger() {}

    //Log attempted logins
    public static void createLog(String username, boolean result) {
        
        try (FileWriter writerF = new FileWriter(FILENAME, true);
            BufferedWriter writerB = new BufferedWriter(writerF);
            PrintWriter writerP = new PrintWriter(writerB)) {
            writerP.println(ZonedDateTime.now() + " " + username + (result ? "Success" : "Failure"));
        } catch (IOException ex) {
            System.out.println("Log error: " + ex.getMessage());
        }
        
    }
    
}

