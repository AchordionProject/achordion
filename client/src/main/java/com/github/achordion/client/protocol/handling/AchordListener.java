package com.github.achordion.client.protocol.handling;

import java.util.EventObject;

public interface AchordListener<EventType extends EventObject> {
    void handleEvent(EventType event);
}
