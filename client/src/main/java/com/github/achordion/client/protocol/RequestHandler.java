package com.github.achordion.client.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHandler {
    static Map<Mtype, BodyHandler> handlers;

    static {
        handlers = new HashMap<Mtype, BodyHandler>();
        handlers.put(Mtype.CHORD, (body) -> {
            int nChords = Utilities.byteArrayToInt(body, 0);
            List<Note> list = new ArrayList<Note>(nChords);
            for(int i = 1; i <= nChords; i++) {
                Note note = Note.values()[Utilities.byteArrayToInt(body, i * 4)];
                list.add(note);
            }
            return list;
        });
    }

    public List<Note> handle(Packet<Mtype> packet) {
        return handlers.get(packet.getType()).handle((packet.getBody()));
    }
}
