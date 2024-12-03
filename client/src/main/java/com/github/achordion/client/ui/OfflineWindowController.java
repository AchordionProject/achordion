package com.github.achordion.client.ui;

import com.github.achordion.client.protocol.core.MType;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;

public class OfflineWindowController {


        @FXML public ChoiceBox<String> Abox;
        @FXML public Label chordSelected;

        public AudioRecorder audioRecorder = new AudioRecorder();

        public void initialize() {
            // Initialize all choice boxes with their options
            setupChoiceBox(Abox, "A", "B", "C", "D", "E", "F", "G");
        }

        private void setupChoiceBox(ChoiceBox<String> box, String noteA, String noteB, String noteC, String noteD, String noteE, String noteF, String noteG) {
            // Add items to the choice box
            box.getItems().addAll(noteA + " sharp", noteA + " minor");
            box.getItems().addAll(noteB + " sharp", noteB + " minor");
            box.getItems().addAll(noteC + " sharp", noteC + " minor");
            box.getItems().addAll(noteD + " sharp", noteD + " minor");
            box.getItems().addAll(noteE + " sharp", noteE + " minor");
            box.getItems().addAll(noteF + " sharp", noteF + " minor");
            box.getItems().addAll(noteG + " sharp", noteG + " minor");



            // Add change listener to immediately play sound when selection changes
            box.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    playChord(newValue);

                    // Display the selected chord on the screen
                    chordSelected.setText(newValue);
                    //System.out.println("Updating label to: " + newValue); // Debug statement
                    //chordSelected.setText("Selected Chord: " + newValue);
                }
            });
        }



    private void playChord(String selectedChord) {
        String audioFilePath;
        switch (selectedChord) {
            case "A sharp":
                audioFilePath = "/com/github/achordion/client/Chords/";
                break;
            case "A minor":
                audioFilePath = "/com/github/achordion/client/Chords/A_minor.wav";
                break;
            //
            case "B sharp":
                audioFilePath = "/com/github/achordion/client/Chords/";
                break;
            case "B minor":
                audioFilePath = "/com/github/achordion/client/Chords/";
                break;
                //
            case "C sharp":
                audioFilePath = "/com/github/achordion/client/Chords/";
                break;
            case "C minor":
                audioFilePath = "/com/github/achordion/client/Chords/";
                break;
            //
            case "D sharp":
                audioFilePath = "/com/github/achordion/client/Chords/";
                break;
            case "D minor":
                audioFilePath = "/com/github/achordion/client/Chords/";
                break;
            //
            case "E sharp":
                audioFilePath = "/com/github/achordion/client/Chords/";
                break;
            case "E minor":
                audioFilePath = "/com/github/achordion/client/Chords/";
                break;
            //
            case "F sharp":
                audioFilePath = "/com/github/achordion/client/Chords/";
                break;
            case "F minor":
                audioFilePath = "/com/github/achordion/client/Chords/";
                break;
            //
            case "G sharp":
                audioFilePath = "/com/github/achordion/client/Chords/";
                break;
            case "G minor":
                audioFilePath = "/com/github/achordion/client/Chords/";
                break;


            default:
                System.err.println("No audio file for the selected chord: " + selectedChord);
                return;
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
