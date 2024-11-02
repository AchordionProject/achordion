package com.github.achordion.client.protocol;

import com.github.achordion.client.protocol.core.Packet;
import com.github.achordion.client.protocol.core.MType;
import com.github.achordion.client.protocol.handling.Note;
import com.github.achordion.client.protocol.handling.events.ChordEvent;
import com.github.achordion.client.protocol.handling.listeners.AchordListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class MainHandler {

    private List<AchordListener<ChordEvent>> chordListeners;

    public MainHandler() {
        this.chordListeners = new ArrayList<>();
    }


    public void handle(Packet<MType> packet) {
        switch (packet.getType()) {
            case CHORD -> {
                ChordEvent event = new ChordEvent(this, handleChordRequest(packet.getBody()));
                sendToAll(chordListeners, event);
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
