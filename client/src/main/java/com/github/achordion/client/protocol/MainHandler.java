package com.github.achordion.client.protocol;

import com.github.achordion.client.protocol.core.MType;
import com.github.achordion.client.protocol.core.Packet;
import com.github.achordion.client.protocol.handling.Note;
import com.github.achordion.client.protocol.handling.events.AudioEvent;
import com.github.achordion.client.protocol.handling.events.ChordEvent;
import com.github.achordion.client.protocol.handling.events.DisconnectEvent;
import com.github.achordion.client.protocol.handling.listeners.AudioListener;
import com.github.achordion.client.protocol.handling.listeners.ChordListener;
import com.github.achordion.client.protocol.handling.listeners.DisconnectListener;

import java.util.ArrayList;
import java.util.List;

public class MainHandler {
    private static final MainHandler instance = new MainHandler();

    List<ChordListener> chordListeners;
    List<DisconnectListener> disconnectListeners;
    List<AudioListener> audioListeners;
    private MainHandler() {
        this.chordListeners = new ArrayList<>();
        this.disconnectListeners = new ArrayList<>();
        this.audioListeners = new ArrayList<>();
    }

    public static MainHandler getInstance() {
        return instance;
    }

    public void handle(Packet<MType> packet) {
        switch (packet.getType()) {
            case CHORD -> {
                System.out.println("CHORD event fired");
                ChordEvent event = new ChordEvent(this, handleChordRequest(packet.getBody()));
                sendChordEvent(event);
            }
            default -> {
                System.out.println("No handler for message type: " + packet.getType());
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

    public void sendDisconnectEvent(DisconnectEvent event) {
        for(DisconnectListener listener : disconnectListeners) {
            listener.onDisconnect(event);
        }
    }

    public void sendChordEvent(ChordEvent event) {
        for(ChordListener listener : chordListeners) {
            listener.onChordEvent(event);
        }
    }
    public void sendAudioEvent(AudioEvent event) {
        for(AudioListener listener : audioListeners) {
            listener.onAudioEvent(event);
        }
    }


    public void addChordListener(ChordListener listener) {
        this.chordListeners.add(listener);
    }

    public void addDisconnectListener(DisconnectListener listener) {
        this.disconnectListeners.add(listener);
    }

    public void addAudioListener(AudioListener listener) {this.audioListeners.add(listener);}
}
