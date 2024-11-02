package com.github.achordion.client.protocol.handling.listeners;

import java.util.EventObject;

public interface AchordListener<EventType extends EventObject> {
    void handleEvent(EventType event);
}
