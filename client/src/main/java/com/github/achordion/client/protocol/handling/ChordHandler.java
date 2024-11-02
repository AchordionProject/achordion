package com.github.achordion.client.protocol.handling;

import com.github.achordion.client.protocol.Utilities;

import java.util.ArrayList;
import java.util.List;

public class ChordHandler implements BodyHandler{

    @Override
    public List<Note> handle(byte[] body) {
        int nChords = Utilities.byteArrayToInt(body, 0);
        List<Note> list = new ArrayList<Note>(nChords);
        for(int i = 1; i <= nChords; i++) {
            Note note = Note.values()[Utilities.byteArrayToInt(body, i * 4)];
            list.add(note);
        }
        return list;
    }
}
