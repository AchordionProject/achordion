package com.github.achordion.client.ui;

import com.github.achordion.client.protocol.*;
import com.github.achordion.client.protocol.core.Connection;
import com.github.achordion.client.protocol.core.Packet;
import com.github.achordion.client.protocol.core.MType;
import com.github.achordion.client.protocol.handling.Note;
import com.github.achordion.client.protocol.handling.events.ChordEvent;
import com.github.achordion.client.protocol.handling.listeners.AchordListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SecondWindowController implements AchordListener<ChordEvent> {
    @FXML
    private Label ipAddressLabel;
    private Connection connection;
    private MainHandler requestHandler;
    Thread receiverThread;
    //this is the variable from the start-view window
    @FXML
    private ToggleButton recordButton;
    @FXML
    public void initialize(){
        String css = getClass().getResource("/com/github/achordion/client/CssStyles/toggleButton.css").toExternalForm();
        recordButton.getStylesheets().add(css);
        recordButton.getStyleClass().add("toggle-button");
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        this.requestHandler = new MainHandler();
        this.requestHandler.addChordListener(this);
        this.receiverThread = new Thread(() -> {
            System.out.println("Thread has started");
            while(true) {
                try {
                    Packet<MType> packet = this.connection.receive();
                    this.requestHandler.handle(packet);
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
        });
        this.receiverThread.start();
    }

    @FXML
    public void sendFile(String filePath){
        try{
            byte[] fileData = Files.readAllBytes(Paths.get(filePath));
            System.out.println("Length is: " + fileData.length);
            Packet<MType> packet = new Packet<>(MType.CHORD, fileData);
            this.connection.send(packet);
            System.out.println("FILE SENT!!!!");
        }catch(IOException e){
            System.out.println("Error reading file");
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
