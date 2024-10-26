package com.github.achordion.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.regex.Pattern;


public class StartPageController {
    @FXML
    //this "AnyText" can be used over and over again with different text inside
    //it has Welcome to Achordion.... then helps display the user input to the screen
    private Label AnyText;
    @FXML
    private TextField textField;
    @FXML
    private Stage primaryStage;
    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
    }
    @FXML
    protected void onClickToConnectClicked() {

        String inputText = textField.getText();
        if(isValidIPAddress(inputText)){
            try {
                //the teacherclickedviewFXML must be loaded
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SecondWindow.fxml"));
                Parent root = loader.load();
                //if successful, the ipaddress will be sent to second controller
                SecondWindowController secondWController = loader.getController();
                secondWController.setIpAddress(inputText);
                //Anytext must be changed
                Stage stage = (Stage) AnyText.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("error");
                alert.setHeaderText("Error");
                alert.setContentText(("Second Window did not open"));
                alert.showAndWait();

            }
            //updates label
            AnyText.setText("Welcome Toooooo Achordion!");
        }
        else{
            invalidIPWarning();

        }
        textField.clear();
    }
    //not used but shows how to bind somthing to the ENTER button
    @FXML
    public void keyPressed(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            System.out.println("blaaaah");
        }
}
// this boolean checks if the ip address is ip4 address
    @FXML
    public boolean isValidIPAddress(String ipAddress) {
        String regex = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return Pattern.compile(regex).matcher(ipAddress).matches();
    }

    @FXML
    public void invalidIPWarning(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("INVALID IP");
        alert.setHeaderText("ERRRROR");
        alert.setContentText("Wrong IP, try again!");


        //set the alert now display it
        alert.showAndWait();
        //when the alert is closed the start window will re-appear
        primaryStage.requestFocus();
    }


}