package com.github.achordion.client.protocol.handling.events;

import com.github.achordion.client.protocol.handling.Note;

import java.util.EventObject;
import java.util.List;

public class ChordEvent extends EventObject {

    private List<Note> notes;

    public ChordEvent(Object source, List<Note> notes) {
        super(source);
        this.notes = notes;
    }

    public List<Note> getNotes() {
        return notes;
    }
}
