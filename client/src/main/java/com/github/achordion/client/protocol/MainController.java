package com.github.achordion.client.protocol;

import com.github.achordion.client.protocol.core.Connection;
import com.github.achordion.client.protocol.core.Packet;
import com.github.achordion.client.protocol.core.MType;
import com.github.achordion.client.protocol.handling.Note;
import com.github.achordion.client.protocol.handling.ReceiverThread;
import com.github.achordion.client.protocol.handling.events.ChordEvent;
import com.github.achordion.client.protocol.handling.listeners.AchordListener;
import com.sun.tools.javac.Main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class MainController {
    private static MainController instance;

    private List<AchordListener<ChordEvent>> chordListeners;

    private Connection connection;
    private ReceiverThread receiverThread;

    private MainController() {
        this.chordListeners = new ArrayList<>();
    }

    public static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    public void connect(String address, int port) throws IOException {
        InetAddress addr = InetAddress.getByName(address);
        Socket socket = new Socket(addr, port);
        this.connection = new Connection(socket);
        this.receiverThread = new ReceiverThread(this);
        this.receiverThread.start();
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return connection != null && connection.isConnected();
    }

    public void handle(Packet<MType> packet) {
        switch (packet.getType()) {
            case CHORD -> {
                System.out.println("CHORD event fired");
                ChordEvent event = new ChordEvent(this, handleChordRequest(packet.getBody()));
                sendToAll(chordListeners, event);
            }
            default -> {
                System.out.println("Weird type of message: " + packet.getType());
            }
        }
    }

    private List<Note> handleChordRequest(byte[] body) {
        int nChords = Utilities.byteArrayToInt(body, 0);
        List<Note> list = new ArrayList<Note>(nChords);
        for(int i = 1; i <= nChords; i++) {
            Note note = Note.values()[Utilities.byteArrayToInt(body, i * 4)];
            list.add(note);
        }
        return list;
    }

    private <T extends EventObject> void sendToAll(List<AchordListener<T>> listeners, T event) {
        for(AchordListener<T> listener : listeners) {
            listener.handleEvent(event);
        }
    }

    public void addChordListener(AchordListener<ChordEvent> listener) {
        this.chordListeners.add(listener);
    }

}
