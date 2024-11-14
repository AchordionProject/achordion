package com.github.achordion.client.protocol;

import com.github.achordion.client.protocol.core.MType;
import com.github.achordion.client.protocol.core.Packet;
import com.github.achordion.client.protocol.music.Chord;
import com.github.achordion.client.protocol.music.Note;
import com.github.achordion.client.protocol.handling.events.ChordEvent;
import com.github.achordion.client.protocol.handling.events.DisconnectEvent;
import com.github.achordion.client.protocol.handling.listeners.ChordListener;
import com.github.achordion.client.protocol.handling.listeners.DisconnectListener;
import com.github.achordion.client.util.Tuple;
import com.github.achordion.client.util.Utilities;

import java.util.ArrayList;
import java.util.List;

public class MainHandler {
    private static final MainHandler instance = new MainHandler();

    List<ChordListener> chordListeners;
    List<DisconnectListener> disconnectListeners;

    private MainHandler() {
        this.chordListeners = new ArrayList<>();
        this.disconnectListeners = new ArrayList<>();
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

    private Tuple<Chord, List<Note>> handleChordRequest(byte[] body) {
        Chord chord = Chord.fromBytes(body[0]);
        int nChords = Utilities.byteArrayToInt(body, 1, 4);
        List<Note> list = new ArrayList<Note>(nChords);
        Note[] values = Note.values();
        int numLoops = nChords % 2 == 0 ? nChords / 2 : nChords / 2 + 1;
        for(int i = 0; i < numLoops; i++) {
            byte twoNotes = body[i + 5];
            for(int j = 1; j >= 0; j--) {
                int noteInd = (twoNotes >> (j * 4)) & 0xF;
                Note note = values[noteInd];
                if(note != Note.ZERO) {
                    list.add(note);
                }
            }
        }
        return new Tuple<>(chord, list);
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


    public void addChordListener(ChordListener listener) {
        this.chordListeners.add(listener);
    }

    public void addDisconnectListener(DisconnectListener listener) {
        this.disconnectListeners.add(listener);
    }
}
