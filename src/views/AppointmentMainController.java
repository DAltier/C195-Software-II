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
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.Appointment;
import models.Customer;

/**
 * FXML Controller class
 *
 * @author Dana Altier
 */

public class AppointmentMainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private AnchorPane appointment;
    
    @FXML
    private TableView<Customer> customerTable;
    
    @FXML 
    private TableColumn<Customer, Integer> customerId;
    
    @FXML
    private TableColumn<Customer, String> customerName;
    
    @FXML
    private Label monthCustomerLabel;
    
    @FXML
    private TableView<Appointment> monthAptTable;
    
    @FXML
    private TableColumn<Appointment, String> monthDescription;
    
    @FXML
    private TableColumn<Appointment, String> monthContact;
    
    @FXML
    private TableColumn<Appointment, String> monthLocation;
    
    @FXML
    private TableColumn<Appointment, String> monthStart;
    
    @FXML
    private TableColumn<Appointment, String> monthEnd;
    
    @FXML
    private Label weekCustomerLabel;
    
    @FXML
    private TableView<Appointment> weekAptTable;
    
    @FXML
    private TableColumn<Appointment, String> weekDescription;
    
    @FXML
    private TableColumn<Appointment, String> weekContact;
    
    @FXML
    private TableColumn<Appointment, String> weekLocation;
    
    @FXML
    private TableColumn<Appointment, String> weekStart;
    
    @FXML
    private TableColumn<Appointment, String> weekEnd;
    
    @FXML 
    private Tab monthly;
    private Customer selectedCust;
    private Appointment selectedAppt;
    private boolean isMonthly;
    
    @FXML
    public void handleCustomerClick(MouseEvent event) {
        
        selectedCust = customerTable.getSelectionModel().getSelectedItem();
        int id = selectedCust.getCustomerId();
        monthCustomerLabel.setText(selectedCust.getCustomerName());
        weekCustomerLabel.setText(selectedCust.getCustomerName());
        monthAptTable.setItems(Appointment.getMonthlyAppointments(id));
        weekAptTable.setItems(Appointment.getWeeklyAppoinments(id));
        
    }
    
    //Handles creating new appointments.
    
    @FXML 
    public void handleAddButton() {
        
        if(customerTable.getSelectionModel().getSelectedItem() != null) {
            selectedCust = customerTable.getSelectionModel().getSelectedItem();
        } else {
            return;
        }
        Dialog<ButtonType> dialog = new Dialog();
        dialog.initOwner(appointment.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AddAppointment.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException ex) {
            System.out.println("AddAppointment Error: " + ex.getMessage());
        }
        ButtonType save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(save);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        AddAppointmentController controller = fxmlLoader.getController();
        controller.populateCustomerName(selectedCust.getCustomerName());
        dialog.showAndWait().ifPresent((response -> {
            if(response == save) {
                if(controller.handleAddAppointment(selectedCust.getCustomerId())) {
                    monthAptTable.setItems(Appointment.getMonthlyAppointments(selectedCust.getCustomerId()));
                    weekAptTable.setItems(Appointment.getWeeklyAppoinments(selectedCust.getCustomerId()));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Appointment Add Error");
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
    
    //Handles modifying selected appointment
    
    @FXML
    public void handleModifyButton() {
        
        if(monthly.isSelected()) {
            if(monthAptTable.getSelectionModel().getSelectedItem() != null) {
                selectedAppt = monthAptTable.getSelectionModel().getSelectedItem();
            } else {
                return;
            }
        } else {
            if(weekAptTable.getSelectionModel().getSelectedItem() != null) {
                selectedAppt = weekAptTable.getSelectionModel().getSelectedItem();
            } else {
                return;
            }
        }
        Dialog<ButtonType> dialog = new Dialog();
        dialog.initOwner(appointment.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("ModifyAppointment.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException ex) {
            System.out.println("ModifyAppointment Error: " + ex.getMessage());
        }
        ButtonType save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(save);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        ModifyAppointmentController controller = fxmlLoader.getController();
        controller.populateFields(selectedCust.getCustomerName(), selectedAppt);
        dialog.showAndWait().ifPresent((response -> {
            if(response == save) {
                if(controller.handleModifyAppointment(selectedAppt.getAppointmentId())) {
                    monthAptTable.setItems(Appointment.getMonthlyAppointments(selectedCust.getCustomerId()));
                    weekAptTable.setItems(Appointment.getWeeklyAppoinments(selectedCust.getCustomerId()));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Appointment Modify Error");
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
    
    //Closes form when pressing the back button
    
    @FXML
    public void handleBackButton(ActionEvent event) {
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
    }
    
    //Handles deletion of selected appointment.
    
    @FXML
    public void handleDeleteButton() {
        
        if(monthly.isSelected()) {
            isMonthly = true;
            if(monthAptTable.getSelectionModel().getSelectedItem() != null) {
                selectedAppt = monthAptTable.getSelectionModel().getSelectedItem();
            } else {
                return;
            }
        } else {
            isMonthly = false;
            if(weekAptTable.getSelectionModel().getSelectedItem() != null) {
                selectedAppt = weekAptTable.getSelectionModel().getSelectedItem();
            } else {
                return;
            }
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Appointment Record");
        alert.setContentText("Are you sure you want to delete this appointment?");
        alert.showAndWait().ifPresent((response -> {
            if(response == ButtonType.OK) {
                Appointment.deleteAppointment(selectedAppt.getAppointmentId());
                if(isMonthly) {
                   monthAptTable.setItems(Appointment.getMonthlyAppointments(selectedCust.getCustomerId())); 
                } else {
                    weekAptTable.setItems(Appointment.getWeeklyAppoinments(selectedCust.getCustomerId()));
                }
            }
        }));
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerTable.setItems(Customer.getAllCustomers());
        
        /**
         * "Appointments Monthly" table will display all appointment for the current month associated with the selected customer
         * "Appointments Weekly" table will display all appointment for the current week associated with the selected customer
         */
        
        //Populates the "Description" column in the "Appointments Monthly" table for selected customer
        monthDescription.setCellValueFactory(cellData -> {
            return cellData.getValue().getDescriptionProperty();
        });
        
        //Populates the "Contact" column in the "Appointments Monthly" table for selected customer
        monthContact.setCellValueFactory(cellData -> {
            return cellData.getValue().getContactProperty();
        });
        
        //Populates the "Location" column in the "Appointments Monthly" table for selected customer
        monthLocation.setCellValueFactory(cellData -> {
            return cellData.getValue().getLocationProperty();
        });
        
        //Populates the "Start" column in the "Appointments Monthly" table for selected customer
        monthStart.setCellValueFactory(cellData -> {
            return cellData.getValue().getStartProperty();
        });
        
        //Populates the "End" column in the "Appointments Monthly" table for selected customer
        monthEnd.setCellValueFactory(cellData -> {
            return cellData.getValue().getEndProperty();
        });
        
        //Populates the "Description" column in the "Appointments Weekly" table for selected customer
        weekDescription.setCellValueFactory(cellData -> {
            return cellData.getValue().getDescriptionProperty();
        });
        
        //Populates the "Contact" column in the "Appointments Weekly" table for selected customer
        weekContact.setCellValueFactory(cellData -> {
            return cellData.getValue().getContactProperty();
        });
        
        //Populates the "Location" column in the "Appointments Weekly" table for selected customer
        weekLocation.setCellValueFactory(cellData -> {
            return cellData.getValue().getLocationProperty();
        });
        
        //Populates the "Start" column in the "Appointments Weekly" table for selected customer
        weekStart.setCellValueFactory(cellData -> {
            return cellData.getValue().getStartProperty();
        });
         
        //Populates the "End" column in the "Appointments Weekly" table for selected customer
        weekEnd.setCellValueFactory(cellData -> {
            return cellData.getValue().getEndProperty();
        });
    }    
    
}