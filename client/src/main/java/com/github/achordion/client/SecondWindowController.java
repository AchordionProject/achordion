package com.github.achordion.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class SecondWindowController {
    @FXML
    private Label ipAddressLabel;
    //this is the variable from the start-view window
    private String ipAddress;
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
        ipAddressLabel.setText("Connected to: "+ ipAddress);
    }
}
