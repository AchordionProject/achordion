package com.github.achordion.client.protocol.handling.listeners;
import com.github.achordion.client.protocol.handling.events.AudioEvent;
public interface AudioListener {
    void onAudioEvent(AudioEvent audioEvent);
}
