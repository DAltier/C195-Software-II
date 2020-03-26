/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import models.Appointment;
import models.Customer;

/**
 * FXML Controller class
 *
 * @author Dana Altier
 */

public class MainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        Appointment appt = Appointment.appointmentIn15Min();
        if(appt != null) {
            Customer cust = Customer.getCustomer(appt.getCustomerId());
            String text = String.format("You have a %s appointment with %s at %s",
                appt.getDescription(), 
                cust.getCustomerName(),
                appt.getTimeFifteen());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Reminder");
            alert.setHeaderText("Appointment Within 15 Minutes");
            alert.setContentText(text);
            alert.showAndWait();
        }
        
    }    

    //Loads the Customer handling form
    
    @FXML
    private void handleCustBtn(ActionEvent event) {
        
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("CustomerMain.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Customer Error: " + e.getMessage());
        }
        
    }

    //Loads the Appointment handling form
    
    @FXML
    private void handleApptBtn(ActionEvent event) {
        
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AppointmentMain.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Appointment Error: " + e.getMessage());
        }
        
    }

    //Loads the Reports handling form
    
    @FXML
    private void handleRptBtn(ActionEvent event) {
        
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Reports.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Reports Error: " + e.getMessage());
        }
        
    }

    //Displays the log.txt file
    
    @FXML
    private void handleLogBtn(ActionEvent event) {
        
        File file = new File("log.txt");
        if(file.exists()) {
            if(Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    System.out.println("Error Opening Log File: " + e.getMessage());
                }
            }
        }
        
    }
    
}
