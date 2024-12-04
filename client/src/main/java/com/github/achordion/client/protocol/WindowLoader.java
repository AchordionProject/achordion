package com.github.achordion.client.protocol;

import com.github.achordion.client.protocol.handling.listeners.ChordListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowLoader {
    //pre-conditions: The loader is not null and the sourceNode is not null. SourceNode can be any UI
    //from the window you are insdie at the time.
    public static void loadWindow(FXMLLoader loader, Node sourceNode)throws IOException {
        if(loader == null || sourceNode == null) {
            System.out.println("Either the loader is null or the source node is null.");
            return;
        }
        Parent root = loader.load();
        Stage stage = (Stage) sourceNode.getScene().getWindow();  // Get existing window
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setMaxHeight(568);
        stage.setMaxWidth(693);  // Changed from setMinHeight to setMaxWidth to match original

        if(MainController.getInstance().isConnected()) {
            ChordListener chordListener = loader.getController();
            MainHandler.getInstance().addChordListener(chordListener);
        }
        stage.show();
    }
}
