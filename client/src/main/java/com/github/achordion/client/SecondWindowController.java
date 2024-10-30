package com.github.achordion.client;

import com.github.achordion.client.protocol.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SecondWindowController {
    @FXML
    private Label ipAddressLabel;
    private Connection connection;
    private RequestHandler requestHandler;
    Thread receiverThread;
    //this is the variable from the start-view window
    @FXML
    private ToggleButton recordButton;
    @FXML
    public void initialize(){
        String css = getClass().getResource("/com/github/achordion/client/CssStyles/toggleButton.css").toExternalForm();
        recordButton.getStylesheets().add(css);
        recordButton.getStyleClass().add("toggle-button");
        this.receiverThread = new Thread(() -> {
            while(true) {
                try {
                    Packet<Mtype> packet = this.connection.receive();
                    List<Note> notes = this.requestHandler.handle(packet);
                    System.out.println(notes);
                } catch (IOException e) {
                }
            }
        });
        this.receiverThread.start();
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        this.requestHandler = new RequestHandler();
    }

    @FXML
    public void sendFile(String filePath){
        try{
            byte[] fileData = Files.readAllBytes(Paths.get(filePath));
            Packet<Mtype> packet = new Packet<>(Mtype.CHORD, fileData);
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

}
