/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Customer;

/**
 * FXML Controller class
 *
 * @author Dana Altier
 */

public class ModifyCustomerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private int id;
    
    @FXML
    private TextField name;
    
    @FXML
    private TextField address;
    
    @FXML
    private ComboBox city;
    
    @FXML
    private TextField country;
    
    @FXML
    private TextField zip;
    
    @FXML
    private TextField phone;
    
    private ObservableList<String> cities = FXCollections.observableArrayList("Phoenix", "New York", "London");
    
    private ObservableList<String> error = FXCollections.observableArrayList();
    
    //Autofills the country field based on city 
    
    @FXML
    public void setCountry() {
        
        String currentCity = city.getSelectionModel().getSelectedItem().toString();
        if(currentCity.equals("London")) {
            country.setText("England");
        } else {
            country.setText("United States");       
        }
        
    }
    
    //Handles modifying selected customer
    
    public boolean handleModifyCustomer() {
        
        error.clear();
        String customerName = name.getText();
        String customerAddress = address.getText();
        int customerCity = city.getSelectionModel().getSelectedIndex() + 1;
        String customerZip = zip.getText();
        String customerPhone = phone.getText();
        if(!validateName(customerName)||!validateAddress(customerAddress)||!validateCity(customerCity)||
                !validateZip(customerZip)||!validatePhone(customerPhone)){
            return false;
        } else {
            return Customer.updateCustomer(id, customerName, customerAddress, customerCity, customerZip, customerPhone);
        }
        
    }
    
    public void populateFields(Customer customer) {
        
        id = customer.getCustomerId();
        name.setText(customer.getCustomerName());
        address.setText(customer.getAddress());
        city.getSelectionModel().select(customer.getCity());
        setCountry();
        phone.setText(customer.getPhone());
        zip.setText(customer.getPostalCode());
        
    }
    
    //Validates customer name field
    
    public boolean validateName(String name) {
        
        if(name.isEmpty()) {
            error.add("You need to enter a name.");
            return false;
        } else {
            return true;
        }
        
    }
    
    //Validates address field
    
    public boolean validateAddress(String address) {
        
        if(address.isEmpty()) {
            error.add("You need to enter an address.");
            return false;
        } else {
            return true;
        }
        
    }
    
    //Validates city selection
    
    public boolean validateCity(int city) {
        
        if(city == 0) {
            error.add("You need to select a city.");
            return false;
        } else {
            return true;
        }
        
    }
    
    //Validates postal code field
    
    public boolean validateZip(String zip) {
        
        if(zip.isEmpty()) {
            error.add("You need to enter a postal code.");
            return false;
        } else {
            return true;
        }
        
    }
    
    //Validates phone field.
    
    public boolean validatePhone(String phone) {
        
        if(phone.isEmpty()) {
            error.add("You need to enter a phone number.");
            return false;
        } else {
            return true;
        }
        
    }
    
    //Displays errors to console.
    
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        city.setItems(cities);
        
    }    
    
}
