package com.github.achordion.client.protocol.handling.events;

import java.util.EventObject;

public class AudioEvent extends EventObject {
    private byte[] audioData; //this will hold the recording

    public AudioEvent(Object source, byte[] audioData) {
        super(source);
        this.audioData = audioData;
    }

    public byte[] getAudioData() {
        return audioData;
    }
}
