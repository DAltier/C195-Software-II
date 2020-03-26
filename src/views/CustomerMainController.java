/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import models.Customer;

/**
 * FXML Controller class
 *
 * @author Dana Altier
 */

public class CustomerMainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private AnchorPane customerMain;
    
    @FXML
    private TableView<Customer> customerTable;
    
    @FXML
    private TableColumn<Customer, Integer> customerId;
    
    @FXML
    private TableColumn<Customer, String> customerName;
    
    private Customer selectedCustomer;
    
    @FXML
    public void handleBackButton(ActionEvent event) {
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
    }
    
    //Handles adding a new customer.
    
    @FXML
    public void handleAddButton() {
        
        Dialog<ButtonType> dialog = new Dialog();
        dialog.initOwner(customerMain.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AddCustomer.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            System.out.println("AddCustomer Error: " + e.getMessage());
        }
        ButtonType save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(save);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        AddCustomerController controller = fxmlLoader.getController();
        dialog.showAndWait().ifPresent((response -> {
            if(response == save) {
                if(controller.handleAddCustomer()) {
                    customerTable.setItems(Customer.getAllCustomers());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Add Customer Error");
                    alert.setContentText(controller.displayErrors());
                    alert.showAndWait().ifPresent((response2 -> {
                        if(response2 == ButtonType.OK) {
                            handleAddButton();
                        }
                    }));
                }
            }
        }));
        
    }
    
    //Handles modifying selected customer
    
    @FXML
    public void handleModifyButton() {
       
        if(customerTable.getSelectionModel().getSelectedItem() != null) {
            selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        } else {
            return;
        }
        Dialog<ButtonType> dialog = new Dialog();
        dialog.initOwner(customerMain.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("ModifyCustomer.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            System.out.println("ModifyCustomer Error: " + e.getMessage());
        }
        ButtonType save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(save);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        ModifyCustomerController controller = fxmlLoader.getController();
        controller.populateFields(selectedCustomer);
        dialog.showAndWait().ifPresent((response -> {
            if(response == save) {
                if(controller.handleModifyCustomer()) {
                    customerTable.setItems(Customer.getAllCustomers());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Modify Customer Error");
                    alert.setContentText(controller.displayErrors());
                    alert.showAndWait().ifPresent((response2 -> {
                        if(response2 == ButtonType.OK) {
                            handleModifyButton();
                        }
                    }));
                }
            }
        }));
        
    }
    
    //Handles removing selected customer from the DB
    
    @FXML
    public void handleDeleteButton() {
        
        if(customerTable.getSelectionModel().getSelectedItem() != null) {
            selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        } else {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Customer Record");
        alert.setContentText("Delete Customer: " + selectedCustomer.getCustomerName() + " ?");
        alert.showAndWait().ifPresent((response -> {
            if(response == ButtonType.OK) {
                Customer.deleteCustomer(selectedCustomer.getCustomerId());
                customerTable.setItems(Customer.getAllCustomers());
            }
        }));
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerTable.setItems(Customer.getAllCustomers());
        
    }    
    
}