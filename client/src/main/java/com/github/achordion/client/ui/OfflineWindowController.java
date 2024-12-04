package com.github.achordion.client.ui;

import com.github.achordion.client.protocol.BackToHome;
import com.github.achordion.client.protocol.core.MType;
import com.github.achordion.client.protocol.handling.listeners.ChordListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;

public class OfflineWindowController {


        @FXML public ChoiceBox<String> Abox;
        @FXML public ChoiceBox<String> Bbox;
        @FXML public ChoiceBox<String> Cbox;
        @FXML public ChoiceBox<String> Dbox;
        @FXML public ChoiceBox<String> Ebox;
        @FXML public ChoiceBox<String> Fbox;
        @FXML public ChoiceBox<String> Gbox;
        @FXML public Button backButton;
        public AudioRecorder audioRecorder = new AudioRecorder();

        public void initialize() {
            // Initialize all choice boxes with their options
            setupChoiceBox(Abox, "A");
            setupChoiceBox(Bbox, "B");
            setupChoiceBox(Cbox, "C");
            setupChoiceBox(Dbox, "D");
            setupChoiceBox(Ebox, "E");
            setupChoiceBox(Fbox, "F");
            setupChoiceBox(Gbox, "G");
        }

        private void setupChoiceBox(ChoiceBox<String> box, String note) {
            // Add items to the choice box
            box.getItems().addAll(note + " major", note + " minor");

            // Add change listener to immediately play sound when selection changes
            box.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    playChord(newValue);
                }
            });
        }

        private void playChord(String selectedChord) {
            String audioFilePath;
//            if (selectedChord.contains("sharp")) {
//                audioFilePath = "/com/github/achordion/client/Chords/c-major.wav";
//            } else {
//                audioFilePath = "/com/github/achordion/client/Chords/A_minor.wav";
//            }
            if(selectedChord.contains("A")){
                if(selectedChord.contains("major")){
                    audioFilePath = "/com/github/achordion/client/Chords/A_major.wav";
                }else{
                    audioFilePath = "/com/github/achordion/client/Chords/A_minor.wav";
                }
            }else if (selectedChord.contains("B")){
                if(selectedChord.contains("major")){
                    audioFilePath = "/com/github/achordion/client/Chords/B_major.wav";
                }else{
                    audioFilePath = "/com/github/achordion/client/Chords/B_minor.wav";
                }
            } else if(selectedChord.contains("C")){
                if(selectedChord.contains("major")){
                    audioFilePath = "/com/github/achordion/client/Chords/C_major.wav";
                }else{
                    audioFilePath = "/com/github/achordion/client/Chords/C_minor.wav";
                }
            }else if (selectedChord.contains("D")){
                if(selectedChord.contains("major")){
                    audioFilePath = "/com/github/achordion/client/Chords/D_major.wav";
                }else{
                    audioFilePath = "/com/github/achordion/client/Chords/D_minor.wav";
                }
            }else if (selectedChord.contains("E")){
                if(selectedChord.contains("major")){
                    audioFilePath = "/com/github/achordion/client/Chords/E_major.wav";
                }else{
                    audioFilePath = "/com/github/achordion/client/Chords/E_minor.wav";
                }
            }else if(selectedChord.contains("F")){
                if(selectedChord.contains("major")){
                    audioFilePath = "/com/github/achordion/client/Chords/F_major.wav";
                }else{
                    audioFilePath = "/com/github/achordion/client/Chords/F_minor.wav";
                }
            }else if(selectedChord.contains("G")){
                if(selectedChord.contains("major")){
                    audioFilePath = "/com/github/achordion/client/Chords/G_major.wav";
                }
                else{
                    audioFilePath = "/com/github/achordion/client/Chords/G_minor.wav";
                }
            } else{
                audioFilePath = "/com/github/achordion/client/Chords/0-minor.wav";
            }
            try {
                URL audioURL = getClass().getResource(audioFilePath);
                if (audioURL != null) {
                    File audioFile = new File(audioURL.getPath());
                    audioRecorder.playAudio(audioFile);
                    System.out.println(selectedChord + " was played");
                } else {
                    System.err.println("Could not find audio file: " + audioFilePath);
                }
            } catch (Exception e) {
                System.err.println("Error playing chord: " + e.getMessage());
            }
        }
    @FXML
    public void onBackButtonClicked(ActionEvent event) {
        BackToHome.goToStart(backButton);
    }

    //create a choice box
//    @FXML
//    public ChoiceBox<String> Abox;
//    @FXML
//    public ChoiceBox<String> Bbox;
//    @FXML
//    public ChoiceBox<String> Cbox;
//    @FXML
//    public ChoiceBox<String> Dbox;
//    @FXML
//    public ChoiceBox<String> Ebox;
//    @FXML
//    public ChoiceBox<String> Fbox;
//    @FXML
//    public ChoiceBox<String> Gbox;
//
//    private AudioRecorder audioRecorder = new AudioRecorder();
//
//    public void initialize() {
//        Abox.getItems().addAll("A sharp", "A minor");
//        Bbox.getItems().addAll("B sharp", "B minor");
//        Cbox.getItems().addAll("C sharp", "C minor");
//        Dbox.getItems().addAll("D sharp", "D minor");
//        Ebox.getItems().addAll("E sharp", "E minor");
//        Fbox.getItems().addAll("F sharp", "F minor");
//        Gbox.getItems().addAll("G sharp", "G minor");
//
//    }
//
//    public void AchordClicked(MouseEvent event) {
//        String pitchSelected = Abox.getValue();
//        if (pitchSelected != null && pitchSelected.equals("A sharp")) {
//            //if A sharp is selected.. insert file path to play it
//            System.out.println("A sharp was clicked");
//
//            //This is the relative path to the file inside the project
//
//            //DEBUG ISSUES ---> DROP DOWN MENU IS FOR A CHORD---FILE IS C-MAJOR
//            //THE FILE DOES NOT PLAY ON THE FIRST MOUSECLICK.. WHY?
//            String audioFilePath = "/com/github/achordion/client/Chords/c-major.wav";
//
//            //a URL object can be converted to a file
//            //URL object is universal.... relative path can be ambiguous
//            URL audioURL = getClass().getResource(audioFilePath);
//
//            //the URL object path is read
//            File audioFile = new File(audioURL.getPath());
//
//            audioRecorder.playAudio(audioFile);
//        }
//        if (pitchSelected != null && pitchSelected.equals("A minor")) {
//            System.out.println("A minor was clicked");
//            String audioFilePath = "/com/github/achordion/client/Chords/A_minor.wav";
//            URL audioURL = getClass().getResource(audioFilePath);
//            File audioFile = new File(audioURL.getPath());
//            audioRecorder.playAudio(audioFile);
//        }
//
//    }
//
//    public void BchordClicked(MouseEvent event) {
//        String pitchSelected = Bbox.getValue();
//        if (pitchSelected != null && pitchSelected.equals("B sharp")) {
//            //if B sharp is selected.. insert file path to play it
//            System.out.println("B sharp was clicked");
//
//            //This is the relative path to the file inside the project
//            String audioFilePath = "/com/github/achordion/client/Chords/c-major.wav";
//
//            //a URL object can be converted to a file
//            //URL object is universal.... relative path can be ambiguous
//            URL audioURL = getClass().getResource(audioFilePath);
//
//            //the URL object path is read
//            File audioFile = new File(audioURL.getPath());
//
//            audioRecorder.playAudio(audioFile);
//        }
//    }
//
//    public void CchordClicked(MouseEvent event) {
//        String pitchSelected = Cbox.getValue();
//        if (pitchSelected != null && pitchSelected.equals("C sharp")) {
//            //if C sharp is selected.. insert file path to play it
//            System.out.println("C sharp was clicked");
//
//            //This is the relative path to the file inside the project
//            String audioFilePath = "/com/github/achordion/client/Chords/c-major.wav";
//
//            //a URL object can be converted to a file
//            //URL object is universal.... relative path can be ambiguous
//            URL audioURL = getClass().getResource(audioFilePath);
//
//            //the URL object path is read
//            File audioFile = new File(audioURL.getPath());
//
//            audioRecorder.playAudio(audioFile);
//        }
//    }
}
