package com.github.achordion.client.ui;

import com.github.achordion.client.protocol.MainController;
import com.github.achordion.client.protocol.MainHandler;
import com.github.achordion.client.protocol.AlertClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.UnknownHostException;


public class StartPageController {
    @FXML
    private TextField textField;
    //SET TO FALSE WHEN RUNNING SERVER + GUI
    //SET TO TRUE WHEN DEBUGGING GUI WINDOWS
    private boolean bypassConnection = false;

    @FXML
    private Stage primaryStage;
    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
        stage.setMaxHeight(568);
        stage.setMaxWidth(693);
    }

    @FXML
    protected void onOfflineClicked() throws IOException {
        System.out.println("The button was clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/github/achordion/client/Windows/OfflineWindow.fxml"));
        Parent root = loader.load();
        OfflineWindowController offlineWindowController = loader.getController();
        Stage stage = (Stage) textField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setMaxHeight(568);
        stage.setMaxWidth(693);
        stage.setMinHeight(519.75);
        stage.setMinWidth(693);
        stage.show();
    }

    @FXML
    protected void onClickToConnectClicked() throws IOException {

        String inputText = textField.getText();

        try {
            if(bypassConnection){
                if(AlertClass.ShowConfirmation("Connection Status", "Welcome to Achordion!", "Click OK to continue")) {
                   try {
                       System.out.println("Connected to Server");
                       System.out.println("bypassconnection");
                       FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/github/achordion/client/Windows/SecondWindow.fxml"));
                       Parent root = loader.load();
                       //if successful, the ipaddress will be sent to second controller
                       SecondWindowController secondWController = loader.getController();
                       Stage stage = (Stage) textField.getScene().getWindow();
                       stage.setScene(new Scene(root));
                       stage.setMaxHeight(568);
                       stage.setMaxWidth(693);
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
                AlertClass.ShowConfirmation("Sucess!!", "ipAddress", "Connected to Server");

                Stage stage = (Stage) textField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setMaxHeight(568);
                stage.setMaxWidth(693);
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