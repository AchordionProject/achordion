package com.github.achordion.client.protocol.handling.listeners;

import com.github.achordion.client.protocol.handling.events.ChordEvent;

public interface ChordListener {
    void onChordEvent(ChordEvent event);
}
