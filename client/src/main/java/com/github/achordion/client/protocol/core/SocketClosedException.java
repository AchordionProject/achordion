package com.github.achordion.client.protocol.core;


import java.io.IOException;

public class SocketClosedException extends IOException {
    public SocketClosedException(String message) {
        super(message);
    }
}
