package com.github.achordion.client.ui;

import com.github.achordion.client.protocol.*;
import com.github.achordion.client.protocol.core.Connection;
import com.github.achordion.client.protocol.core.Packet;
import com.github.achordion.client.protocol.core.MType;
import com.github.achordion.client.protocol.handling.events.AudioEvent;
import com.github.achordion.client.protocol.handling.events.ChordEvent;
import com.github.achordion.client.protocol.handling.events.DisconnectEvent;
import com.github.achordion.client.protocol.handling.listeners.AudioListener;
import com.github.achordion.client.protocol.handling.listeners.ChordListener;
import com.github.achordion.client.protocol.handling.listeners.DisconnectListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class SecondWindowController implements ChordListener, DisconnectListener, AudioListener {

    private MainController mainController;
    @FXML
    private Label chords;
    @FXML
    private ToggleButton recordButton;
    @FXML
    private Button BackButton;
    @FXML
    private Button browseFileButton;
    private AudioRecorder audioRecorder;

    @FXML
    public void initialize() {
        /*
        this.audioRecorder= new AudioRecorder();
        String css = getClass().getResource("/com/github/achordion/client/CssStyles/toggleButton.css").toExternalForm();
        recordButton.getStylesheets().add(css);
        recordButton.getStyleClass().add("toggle-button");
        */
        this.mainController = MainController.getInstance();
        Font font = Font.loadFont(getClass().getResource("/com/github/achordion/client/Fonts/VCR_OSD_MONO_1.001.ttf").toExternalForm(), 20);
        this.chords.setFont(font);
    }


    @FXML
    public void sendFile(File file){
        if(!this.mainController.isConnected()) { return; }
        Connection connection = this.mainController.getConnection();
        Path filePath = file.toPath();
        try{
            InputStream is = new FileInputStream(file);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            GZipper.gzip(is, os);
            Packet<MType> packet = new Packet<>(MType.CHORD, os.toByteArray());
            connection.send(packet);
        }catch(FileNotFoundException e){
           AlertClass.ShowError("Error","File not Found error","The file '"+filePath+"' could  not be found");
        }
        catch(IOException e){
            AlertClass.ShowError("Error","Error",( "File found but not reading " + e.getMessage()));
        }
    }
    /*
    @FXML
    public void onRecordingClicked(ActionEvent event){
            if(recordButton.isSelected()) {
                sendFile(new File("c-major.wav"));
                recordButton.setText("Stop Recording");
                System.out.println("Recording is selected");
            }else
                System.out.println("Recording is not selected")
        if(recordButton.isSelected()) {
            audioRecorder.startRecording();
            recordButton.setText("Stop Recording");
            System.out.println("Recording started");

        }else{
            audioRecorder.stopRecording();
            recordButton.setText("Recording stopped");
            System.out.println("Recording stopped");
        }
    }
*/
    @FXML
    public void onFileBrowse(ActionEvent event){
        if(browseFileButton.isFocused()) {
            Stage stage = (Stage) browseFileButton.getScene().getWindow();
            File file = chooseFile(stage);
            sendFile(file);
        }
    }

    private File chooseFile(Stage stage) {
         FileChooser fileChooser = new FileChooser();
         fileChooser.setTitle("Select a File");
         fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
         FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Audio Files (*.wav)", "*.wav");
         fileChooser.getExtensionFilters().add(filter);
         return fileChooser.showOpenDialog(stage);
     }

    @Override
    public void onChordEvent(ChordEvent event) {
        Platform.runLater(() -> this.chords.setText(event.getData().toString()) );
        System.out.println(event.getData());
    }

    @Override
    public void onDisconnect(DisconnectEvent event) {
        Platform.runLater(() -> BackToHome.ExitConnectionToHome(BackButton, mainController));
    }

    @Override
    public void onAudioEvent(AudioEvent event){
        byte[] audioData = event.getAudioData();
        System.out.println("Here it is as a string"+ Arrays.toString(audioData) + "with length"+ audioData.length + "bytes of data");
    }
    @FXML
    public void onBackButtonClicked(ActionEvent event) {
        BackToHome.ExitConnectionToHome(BackButton, mainController);
    }

}
