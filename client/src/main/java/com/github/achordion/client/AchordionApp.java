package com.github.achordion.client;

import com.github.achordion.client.ui.StartPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.IOException;

public class AchordionApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AchordionApp.class.getResource("/com/github/achordion/client/Windows/Start-View.fxml"));
       //  root is put into the scene below
        Parent root = fxmlLoader.load();
        //setting reference to start screen
        StartPageController startController = fxmlLoader.getController();
        startController.setPrimaryStage(stage);
        Scene scene = new Scene(root, 980, 600);
        stage.setTitle("Achordion.exe");
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setFullScreenExitHint("");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
