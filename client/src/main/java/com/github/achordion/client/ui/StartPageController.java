package com.github.achordion.client.ui;

import com.github.achordion.client.protocol.MainController;
import com.github.achordion.client.protocol.MainHandler;
import com.github.achordion.client.protocol.AlertClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.UnknownHostException;
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/github/achordion/client/Windows/SecondWindow.fxml"));
                Parent root = loader.load();
                //if successful, the ipaddress will be sent to second controller
                SecondWindowController secondWController = loader.getController();
                MainController maincontroller = MainController.getInstance();
                MainHandler mainhandler = MainHandler.getInstance();
                maincontroller.connect(inputText, 60000);
                mainhandler.addChordListener(secondWController);
                AlertClass.ShowError("Sucess!!", "ipAddress", "Connected to Server");

                Stage stage = (Stage) AnyText.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (UnknownHostException e) {
                AlertClass.ShowError("Unknown host","Server Error","Could not connect to IP address.. " + e.getMessage());
            }catch(IOException e){
                AlertClass.ShowError("Connection Error","Server not connected","Failed to connect.. Error: " + e.getMessage());
            }
        }
        else{
            AlertClass.ShowError("Invalid IP","Error","Going back to start page, retry IP address");
            primaryStage.requestFocus();
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