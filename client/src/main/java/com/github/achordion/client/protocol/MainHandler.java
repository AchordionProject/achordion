package com.github.achordion.client.protocol;

import com.github.achordion.client.protocol.core.Packet;
import com.github.achordion.client.protocol.core.MType;
import com.github.achordion.client.protocol.handling.BodyHandler;
import com.github.achordion.client.protocol.handling.ChordHandler;
import com.github.achordion.client.protocol.handling.Note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainHandler {
    private final Map<MType, BodyHandler> strategies;

    public MainHandler() {
        strategies = new HashMap<>();
        this.strategies.put(MType.CHORD, new ChordHandler());
    }


    public List<Note> handle(Packet<MType> packet) {
        return this.strategies.get(packet.getType()).handle((packet.getBody()));
    }
}
