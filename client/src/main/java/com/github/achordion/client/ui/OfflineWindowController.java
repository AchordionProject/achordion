package com.github.achordion.client.ui;

import com.github.achordion.client.protocol.core.MType;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;

public class OfflineWindowController {
    //create a choice box
    @FXML
    public ChoiceBox<String> Abox;

    private AudioRecorder audioRecorder = new AudioRecorder();
    public void initialize() {
        Abox.getItems().addAll("A sharp","A minor");

    }

    public void AchordClicked(MouseEvent event) {
        String pitchSelected = Abox.getValue();
        if (pitchSelected != null && pitchSelected.equals("A sharp")) {
            //if A sharp is selected.. insert file path to play it
            System.out.println("A sharp was clicked");

           //This is the relative path to the file inside the project

            //DEBUG ISSUES ---> DROP DOWN MENU IS FOR A CHORD---FILE IS C-MAJOR
            //THE FILE DOES NOT PLAY ON THE FIRST MOUSECLICK.. WHY?
            String audioFilePath = "/com/github/achordion/client/Chords/c-major.wav";

            //a URL object can be converted to a file
            //URL object is universal.... relative path can be ambiguous
            URL audioURL = getClass().getResource(audioFilePath);

            //the URL object path is read
            File audioFile = new File(audioURL.getPath());

            audioRecorder.playAudio(audioFile);
        }
        if(pitchSelected != null && pitchSelected.equals("A minor")) {
            System.out.println("A minor was clicked");
            String audioFilePath = "/com/github/achordion/client/Chords/A_minor.wav";
            URL audioURL = getClass().getResource(audioFilePath);
            File audioFile = new File(audioURL.getPath());
            audioRecorder.playAudio(audioFile);
        }
    }
}
