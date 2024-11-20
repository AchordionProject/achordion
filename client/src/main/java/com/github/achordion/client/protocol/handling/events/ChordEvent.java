package com.github.achordion.client.protocol.handling.events;

import com.github.achordion.client.protocol.music.Chord;
import com.github.achordion.client.protocol.music.Note;
import com.github.achordion.client.util.Tuple;

import java.util.EventObject;
import java.util.List;

public class ChordEvent extends EventObject {

    private Tuple<Chord, List<Note>> data;

    public ChordEvent(Object source, Tuple<Chord, List<Note>> data) {
        super(source);
        this.data= data;
    }

    public Tuple<Chord, List<Note>> getData() {
        return this.data;
    }
}
