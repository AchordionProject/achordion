package com.github.achordion.client.protocol.handling.listeners;

import com.github.achordion.client.protocol.handling.events.DisconnectEvent;

public interface DisconnectListener {
    void onDisconnect(DisconnectEvent event);
}
