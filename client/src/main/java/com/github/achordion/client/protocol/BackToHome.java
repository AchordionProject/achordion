package com.github.achordion.client.protocol;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;

//the purpose of this class is to allow for easy transition to the home page
public class BackToHome {
    //This function does not include exiting out of the main controller
    //Should only be used for offline button
    public static void goToStart(Button backButton){
        try {
            FXMLLoader loader = new FXMLLoader(BackToHome.class.getResource("/com/github/achordion/client/Windows/Start-View.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        }catch(IOException e){
            AlertClass.ShowError("ERROR","ERROR","Error loading the start page");
        }
    }
    //this will be called from the server window
    //it accounts for disconnecting the maincontroller prior to returning back to the home page
    public static void ExitConnectionToHome(Button ServerBackButton, MainController mainController ){
        if(mainController != null && mainController.isConnected()) {
            try {
                mainController.disconnect();
            } catch(IOException e) {
                AlertClass.ShowError("Error", "Error", "Error disconnecting from main controller");
            }
        }
        goToStart(ServerBackButton);
    }
}