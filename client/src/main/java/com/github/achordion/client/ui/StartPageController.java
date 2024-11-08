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
    private TextField textField;
    //SET TO FALSE WHEN RUNNING SERVER + GUI
    //SET TO TRUE WHEN DEBUGGING GUI WINDOWS
    private boolean bypassConnection = true;
    @FXML
    private Stage primaryStage;

    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
    }

    @FXML
    protected void onOfflineClicked() throws IOException {
        System.out.println("The button was clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/github/achordion/client/Windows/ThirdWindow.fxml"));
        Parent root = loader.load();
        OfflineWindowController offlineWindowController = loader.getController();
        Stage stage = (Stage) textField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    protected void onClickToConnectClicked() throws IOException {

        String inputText = textField.getText();
        if(!isValidIPAddress(inputText)) {
            AlertClass.ShowError("Invalid IP", "Error", "Going back to start page, retry IP address");
            primaryStage.requestFocus();
            return;
        }
        try {
            //This was needed to allow work on the second window
            //does not alter server functionality
            if(bypassConnection) {
                System.out.println("Bypassing connection!!!");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/github/achordion/client/Windows/SecondWindow.fxml"));
                Parent root = loader.load();
                SecondWindowController secondWindowController = loader.getController();
                Stage stage = (Stage) textField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/github/achordion/client/Windows/SecondWindow.fxml"));
                Parent root = loader.load();
                //if successful, the ipaddress will be sent to second controller
                SecondWindowController secondWController = loader.getController();
                MainController maincontroller = MainController.getInstance();
                MainHandler mainhandler = MainHandler.getInstance();
                maincontroller.connect(inputText, 60000);
                mainhandler.addChordListener(secondWController);
                AlertClass.ShowError("Sucess!!", "ipAddress", "Connected to Server");

                Stage stage = (Stage) textField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (UnknownHostException e) {
            AlertClass.ShowError("Unknown host","Server Error","Could not connect to IP address.. " + e.getMessage());
        } catch(IOException e){
            AlertClass.ShowError("Connection Error","Server not connected","Failed to connect.. Error: " + e.getMessage());
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