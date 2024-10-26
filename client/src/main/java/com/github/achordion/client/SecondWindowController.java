package com.github.achordion.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class SecondWindowController {
    @FXML
    private Label ipAddressLabel;
    @FXML
    public void setIpAddress(String ipAddress){
        if(ipAddress != null){
            ipAddressLabel.setText("Connected to: "+ ipAddress);
        }else{
            System.out.println("ipAddress was not initialized properly");
        }

    }
}
