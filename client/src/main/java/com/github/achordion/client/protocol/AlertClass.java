package com.github.achordion.client.protocol;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

//import javafx.scene.control.ButtonType;
public class AlertClass {
    public static void ShowError(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

//**************************//
//Other types of Alerts.
// Show Confirmation
// Show Information
// Show Warning
// Show None
//**************************//
