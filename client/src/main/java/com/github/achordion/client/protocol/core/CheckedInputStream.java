package com.github.achordion.client.protocol.core;

import java.io.IOException;
import java.io.InputStream;

public class CheckedInputStream extends InputStream {
    private InputStream in;

    public CheckedInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException{
        int bytesRead = this.in.read();
        if (bytesRead == -1) {
            throw new SocketClosedException("Socket was closed by outside entity.");
        }
        return bytesRead;
    }
}
