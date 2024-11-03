package com.github.achordion.client.ui;

import com.github.achordion.client.protocol.*;
import com.github.achordion.client.protocol.core.Connection;
import com.github.achordion.client.protocol.core.Packet;
import com.github.achordion.client.protocol.core.MType;
import com.github.achordion.client.protocol.handling.events.ChordEvent;
import com.github.achordion.client.protocol.handling.AchordListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.event.ActionEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SecondWindowController implements AchordListener<ChordEvent> {

    private MainController mainController;

    @FXML
    private ToggleButton recordButton;
    @FXML
    public void initialize() {
        String css = getClass().getResource("/com/github/achordion/client/CssStyles/toggleButton.css").toExternalForm();
        recordButton.getStylesheets().add(css);
        recordButton.getStyleClass().add("toggle-button");
        this.mainController = MainController.getInstance();
    }

    @FXML
    public void sendFile(String filePath){
        if(!this.mainController.isConnected()) { return; }
        Connection connection = this.mainController.getConnection();
        try{
            byte[] fileData = Files.readAllBytes(Paths.get(filePath));
            Packet<MType> packet = new Packet<>(MType.CHORD, fileData);
            connection.send(packet);
        }catch(FileNotFoundException e){
           AlertClass.ShowError("Error","File not Found error","The file '"+filePath+"' could  not be found");
        }
        catch(IOException e){
            AlertClass.ShowError("Error","Error",( "File found but not reading " + e.getMessage()));
        }
    }
    @FXML
    public void onRecordingClicked(ActionEvent event){
            if(recordButton.isSelected()) {
                sendFile("c-major.wav");
                recordButton.setText("Stop Recording");
                System.out.println("Recording is selected");
            }else
                System.out.println("Recording is not selected");
    }

    @Override
    public void handleEvent(ChordEvent event) {
        System.out.println(event.getNotes());
    }
}
