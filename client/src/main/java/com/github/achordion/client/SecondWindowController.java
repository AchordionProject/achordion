package com.github.achordion.client;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.OutputStream;
import java.net.Socket;

public class SecondWindowController {
    @FXML
    private Label ipAddressLabel;
    //this is the variable from the start-view window
    private String ipAddress;
    @FXML
    private ToggleButton recordButton;
    @FXML
    public OutputStream outputStream;
    @FXML
    public void initialize(){
        String css = getClass().getResource("/com/github/achordion/client/CssStyles/toggleButton.css").toExternalForm();
        recordButton.getStylesheets().add(css);
        recordButton.getStyleClass().add("toggle-button");
    }
    @FXML
    public void setIpAddress(String ipAddress){
        //the ipAddress from the preview winodow has been verified if we make it this far
        //now storing it in ipAddress for further use
        this.ipAddress = ipAddress;
        if(ipAddress != null){
            ipAddressLabel.setText("Connected to: "+ ipAddress);
        }else{
            System.out.println("ipAddress was not initialized properly");
        }
    }

    //function to connect to IP address
    //not sure what IP we will use but this is where we should connect to it.
    @FXML
    public void connectToAddress(){
        //ipAddress is the address the user entered
        //just sets a label with it now
        try{
            Socket Achordionsocket = new Socket(ipAddress, 60000);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connected!");
            alert.setHeaderText("ipAddress");
            alert.setContentText(("Connected to server"));
            alert.showAndWait();
            outputStream = Achordionsocket.getOutputStream();
        }catch(IOException e){
            System.out.println("Error connecting to server");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR");
            alert.setContentText(("Problem connecting to the server"));
            alert.showAndWait();
        }
        ipAddressLabel.setText("Connected to: "+ ipAddress);
    }
    @FXML
    public void sendFile(String filePath){
        try{
            byte[] fileData = Files.readAllBytes(Paths.get(filePath));
            outputStream.write(fileData);
            System.out.println("FILE SENT!!!!");
        }catch(IOException e){
            System.out.println("Error reading file");
        }
    }
    @FXML
    public void onRecordingClicked(ActionEvent event){
            if(recordButton.isSelected()) {
                recordButton.setText("Stop Recording");
                System.out.println("Recording is selected");
            }else
                System.out.println("Recording is not selected");
    }

}
