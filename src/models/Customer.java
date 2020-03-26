/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.time.LocalDate.now;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Database;

/**
 *
 * @author Dana Altier
 */

public class Customer {
    
    private final SimpleIntegerProperty customerId = new SimpleIntegerProperty();
    private final SimpleStringProperty customerName = new SimpleStringProperty();
    private final SimpleStringProperty address = new SimpleStringProperty();
    private final SimpleStringProperty city = new SimpleStringProperty();
    private final SimpleStringProperty postalCode = new SimpleStringProperty();
    private final SimpleStringProperty phone = new SimpleStringProperty();
    
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    
    public Customer() {
    
    }
    
    public Customer(int id, String name, String address, String city, String phone, String postalCode) {
        
        setCustomerId(id);
        setCustomerName(name);
        setAddress(address);
        setCity(city);
        setPhone(phone);
        setPostalCode(postalCode);
        
    }
    
    //Getters and setters for Customer
    
    public int getCustomerId() {
        
        return customerId.get();
        
    }
    
    public String getCustomerName() {
        
        return customerName.get();
        
    }
    
    public String getAddress() {
        
        return address.get();
        
    }
    
    public String getCity() {
        
        return city.get();
        
    }
    
    public String getPhone() {
        
        return phone.get();
        
    }
    
    public String getPostalCode() {
        
        return postalCode.get();
        
    }
    
    public void setCustomerId(int customerId) {
        
        this.customerId.set(customerId);
        
    }
    
    public void setCustomerName(String customerName) {
        
        this.customerName.set(customerName);
        
    }
    
    public void setAddress(String address) {
        
        this.address.set(address);
        
    }
    
    public void setCity(String city) {
        
        this.city.set(city);
        
    }
    
    public void setPhone(String phone) {
        
        this.phone.set(phone);
        
    }
    
    public void setPostalCode(String postalCode) {
        
        this.postalCode.set(postalCode);
        
    }
    
    //Retrieves a customer based on ID
    
    public static Customer getCustomer(int id) {
        
        try {
            Statement statement = Database.getDBConnection().createStatement();
            String query = "SELECT * FROM customer WHERE customerId='" + id + "'";
            ResultSet results = statement.executeQuery(query);
            if(results.next()) {
                Customer customer = new Customer();
                customer.setCustomerName(results.getString("customerName"));
                return customer;
            }
            statement.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return null;
        
    }

    //List of all the customers in the DB
    
    public static ObservableList<Customer> getAllCustomers() {
        
        allCustomers.clear();
        try {
            Statement statement = Database.getDBConnection().createStatement();
            String query = "SELECT customer.customerId, customer.customerName, address.address, address.phone, address.postalCode, city.city"
                + " FROM customer INNER JOIN address ON customer.addressId = address.addressId "
                + "INNER JOIN city ON address.cityId = city.cityId";
            ResultSet results = statement.executeQuery(query);
            while(results.next()) {
                Customer customer = new Customer(
                    results.getInt("customerId"), 
                    results.getString("customerName"), 
                    results.getString("address"),
                    results.getString("city"),
                    results.getString("phone"),
                    results.getString("postalCode"));
                allCustomers.add(customer);
            }
            statement.close();
            return allCustomers;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
        
    }
    
    // Saves new Customer to the DB
    
    public static boolean saveCustomer(String name, String address, int cityId, String zip, String phone) {
        
        try {
            Statement statement = Database.getDBConnection().createStatement();
            String queryOne = "INSERT INTO address SET address='" + address + "', address2='" + null + "', phone='" + phone + "', postalCode='" + zip 
                    + "', cityId='" + cityId + "', createDate='" + now() + "', createdBy='', lastUpdateBy=''";
            int updateOne = statement.executeUpdate(queryOne);
            if(updateOne == 1) {
                int addressId = Customer.getAllCustomers().size() + 1;
                String queryTwo = "INSERT INTO customer SET customerName='" + name + "', addressId='" + addressId + "', active=1 , createDate='" + now() 
                        + "', createdBy='', lastUpdateBy=''";
                int updateTwo = statement.executeUpdate(queryTwo);
                if(updateTwo == 1) {
                    return true;
                }
            }
            statement.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return false;
        
    }
    
    // Updates selected Customer in the DB
    
    public static boolean updateCustomer(int id, String name, String address, int cityId, String zip, String phone) {
        
        try {
            Statement statement = Database.getDBConnection().createStatement();
            String queryOne = "UPDATE address SET address='" + address + "', cityId=" + cityId + ", postalCode='" + zip + "', phone='" + phone + "' "
                + "WHERE addressId=" + id;
            int updateOne = statement.executeUpdate(queryOne);
            if(updateOne == 1) {
                String queryTwo = "UPDATE customer SET customerName='" + name + "', addressId=" + id + " WHERE customerId=" + id;
                int updateTwo = statement.executeUpdate(queryTwo);
                if(updateTwo == 1) {
                    return true;
                }
            }
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
        
    }
    
    //Removes selected Customer from the DB
    
    public static boolean deleteCustomer(int id) {
        
        try {
            Statement statement = Database.getDBConnection().createStatement();
            String queryOne = "DELETE FROM appointment WHERE customerId=" + id;
            int updateOne = statement.executeUpdate(queryOne);
            String queryTwo = "DELETE FROM customer WHERE customerId=" + id;
            int updateTwo = statement.executeUpdate(queryTwo);
            if(updateTwo == 1) {
//                String queryThree = "DELETE FROM address WHERE addressId=" + id;
//                int updateThree = statement.executeUpdate(queryThree);
//                if(updateThree == 1) {
                    return true;
//                }  
            }
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
        
    }
        
}
