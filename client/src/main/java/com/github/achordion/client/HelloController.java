package com.github.achordion.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class HelloController {
    @FXML
    //this "AnyText" can be used over and over again with different text inside
    //it has Welcome to Achordion.... then helps display the user input to the screen
    private Label AnyText;
    @FXML
    private TextField textField;
    @FXML
    private Label outputLabel;

    @FXML
    protected void onStartPageButtonClick() {
        AnyText.setText("Welcome to Achordion!");
    }

    @FXML
    //so far it only displays what the user entered for debugging pursposes
    public void onUserInput(){
        String inputText = textField.getText();
        AnyText.setText(inputText);
        textField.clear();
    }
    //this will call onUserInput when the enter button is pressed inside the text field
    @FXML
    public void keyPressed(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            onUserInput();
        }
    }

}