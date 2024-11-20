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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.UnknownHostException;


public class StartPageController {
    @FXML
    //this "AnyText" can be used over and over again with different text inside
    //it has Welcome to Achordion.... then helps display the user input to the screen
    private Label AnyText;
    @FXML
    private TextField textField;
    private boolean bypassconnection = true;
    @FXML
    private Stage primaryStage;

    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
    }

    @FXML
    protected void onClickToConnectClicked() {

        String inputText = textField.getText();

        try {
            if(bypassconnection){
                if(AlertClass.ShowConfirmation("Connection Status", "Welcome to Achordion!", "Click OK to continue")) {
                   try {
                       System.out.println("Connected to Server");
                       System.out.println("bypassconnection");
                       FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/github/achordion/client/Windows/SecondWindow.fxml"));
                       Parent root = loader.load();
                       //if successful, the ipaddress will be sent to second controller
                       SecondWindowController secondWController = loader.getController();
                       Stage stage = (Stage) AnyText.getScene().getWindow();
                       stage.setScene(new Scene(root));
                       stage.show();
                       MainHandler mainhandler = MainHandler.getInstance();
                       mainhandler.addAudioListener(secondWController);
                   }catch(IOException e){
                       AlertClass.ShowError("Connection Error","Server not connected","Failed to connect.. Error: " + e.getMessage());
                   }
                }else{
                    //This is what will happen if "Cancel" is clicked
                    //Or if the red X is clicked
                    primaryStage.requestFocus();
                    return;
                }
            }else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/github/achordion/client/Windows/SecondWindow.fxml"));
                Parent root = loader.load();
                //if successful, the ipaddress will be sent to second controller
                SecondWindowController secondWController = loader.getController();
                MainController maincontroller = MainController.getInstance();
                MainHandler mainhandler = MainHandler.getInstance();
                maincontroller.connect(inputText, 60000);
                mainhandler.addChordListener(secondWController);
                mainhandler.addDisconnectListener(secondWController);
                mainhandler.addAudioListener(secondWController);
                AlertClass.ShowError("Sucess!!", "ipAddress", "Connected to Server");

                Stage stage = (Stage) AnyText.getScene().getWindow();
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
}