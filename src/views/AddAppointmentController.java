/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Appointment;

/**
 * FXML Controller class
 *
 * @author Dana Altier
 */

public class AddAppointmentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField customerName;
    
    @FXML
    private ComboBox contact;
    
    @FXML
    private TextField location;
    
    @FXML 
    private DatePicker date;
    
    @FXML
    private ComboBox time;
    
    @FXML
    private ComboBox type;
    
    private final ObservableList<String> contacts = FXCollections.observableArrayList("Jamie", "Daniel", "Susan", "Mark", "Laurel", "test");
    private final ObservableList<String> times = FXCollections.observableArrayList("9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "13:00 PM", "14:00 PM", "15:00 PM", "16:00 PM");
    private final ObservableList<String> types = FXCollections.observableArrayList("Intro: First Meeting", "Consult: Retirement", "Consult: Stock", "Consult: Tax");
    private ObservableList<String> error = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        contact.setItems(contacts);
        time.setItems(times);
        type.setItems(types);
        date.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(
                    empty || 
                    date.getDayOfWeek() == DayOfWeek.SATURDAY || 
                    date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                    date.isBefore(LocalDate.now()));
                if(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isBefore(LocalDate.now())) {
                    setStyle("-fx-background-color: #ffc4c4;");
                }
            }
        });
        
    }    
    
    public void populateCustomerName(String name) {
        
        customerName.setText(name);
        
    }
    
    //Creates new appointment for selected customer
    
    public boolean handleAddAppointment(int id) {
        
        error.clear();
        int aptContact = contact.getSelectionModel().getSelectedIndex();
        int aptType = type.getSelectionModel().getSelectedIndex();
        int aptTime = time.getSelectionModel().getSelectedIndex();
        LocalDate ld = date.getValue();
        if(!validateContact(aptContact)||!validateType(aptType)||!validateTime(aptTime)||!validateDate(ld)) {
            return false;
        }
        if(Appointment.overlappingAppointment(-1, location.getText(), ld.toString(), times.get(aptTime))) {
            error.add("Overlapping Appointments.");
            return false;
        }
        if(Appointment.saveAppointment(id, types.get(aptType), contacts.get(aptContact), location.getText(), ld.toString(), times.get(aptTime))) {
            return true;
        } else {
            error.add("Database Error.");
            return false;
        }
        
    }
    
    //Validates contact field
    
    public boolean validateContact(int aptContact) {
        
        if(aptContact == -1) {
            error.add("You need to select a contact.");
            return false;
        } else {
            return true;
        }
        
    }
    
    //Validates type field selection
    
    public boolean validateType(int aptType) {
        
        if(aptType == -1) {
            error.add("You need to select an appointment type.");
            return false;
        } else {
            return true;
        }
        
    }
    
    //Validates time field selection
    
    public boolean validateTime(int aptTime) {
        
        if(aptTime == -1) {
            error.add("You need to select an appointment time.");
            return false;
        } else {
            return true;
        }
        
    }
    
    //Validates date field selection
    
    public boolean validateDate(LocalDate aptDate) {
        
        if(aptDate == null) {
            error.add("You need to select an appointment date.");
            return false;
        } else {
            return true;
        }
        
    }
    
    //Displays errors to the console
    
    public String displayErrors(){
        
        String e = "";
        if(error.size() > 0) {
            for(String err : error) {
                e = e.concat(err);
            }
            return e;
        } else {
            e = "Database Error";
            return e;
        }
        
    }
    
    //Associates consultants and locations
    
    @FXML
    public void handleLocation() {
        
        String c = contact.getSelectionModel().getSelectedItem().toString();
        if((c.equals("Jamie")) || (c.equals("Daniel"))) {
            location.setText("London");
        } else if((c.equals("Susan")) || (c.equals("Mark"))) {
            location.setText("New York");
        } else if((c.equals("Laurel")) || (c.equals("test"))) {
            location.setText("Phoenix");
        }
        
    }
        
}