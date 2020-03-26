/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.User;

/**
 *
 * @author Dana Altier
 */

public class LoginController implements Initializable {
    
    @FXML
    private TextField usernameTxt;
    
    @FXML
    private PasswordField passwordTxt;
    
    @FXML
    private AnchorPane messagePane;
    
    @FXML
    private Label usernameLbl;
    
    @FXML
    private Label passwordLbl;
    
    @FXML
    private Label mainMessage;
    
    @FXML 
    private Label langMessage;
    
    @FXML
    private Button loginBtn;
    
    private String errorHeader;
    private String errorTitle;
    private String errorText;
    
    //Handles logging in current user, if it exists in the DB
    
    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        boolean validUser = User.login(username, password);
        if(validUser) {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errorTitle);
            alert.setHeaderText(errorHeader);
            alert.setContentText(errorText);
            alert.showAndWait();
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Locale.setDefault(new Locale("es"));
        Locale locale = Locale.getDefault();
        rb = ResourceBundle.getBundle("languages/login", locale);
        usernameLbl.setText(rb.getString("username"));
        passwordLbl.setText(rb.getString("password"));
        loginBtn.setText(rb.getString("login"));
        mainMessage.setText(rb.getString("message"));
        langMessage.setText(rb.getString("language"));
        errorHeader = rb.getString("errorheader");
        errorTitle = rb.getString("errortitle");
        errorText = rb.getString("errortext");
        
    }    
    
}