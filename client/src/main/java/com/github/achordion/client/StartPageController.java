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

import java.util.regex.Pattern;


public class StartPageController {
    @FXML
    //this "AnyText" can be used over and over again with different text inside
    //it has Welcome to Achordion.... then helps display the user input to the screen
    private Label AnyText;
    @FXML
    private TextField textField;

    @FXML
    protected void onClickToConnectClicked() {

        String inputText = textField.getText();
        if(isValidIPAddress(inputText)){
            try {
                //the teacherclickedviewFXML must be loaded
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SecondWindow.fxml"));
                Parent root = loader.load();

                SecondWindowController secondWController = loader.getController();
                secondWController.setIpAddress(inputText);
                //Anytext must be changed
                Stage stage = (Stage) AnyText.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            AnyText.setText("Welcome Toooooo Achordion!");
        }
        else{
            AnyText.setText("Invalid IP Address!");

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
}