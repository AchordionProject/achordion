package com.github.achordion.client.protocol.core;

import com.github.achordion.client.protocol.Utilities;

import java.io.*;
import java.net.Socket;

public class Connection {
    private Socket socket;
    private OutputStream out;
    private InputStream in;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = socket.getOutputStream();
        this.in = socket.getInputStream();
    }

    public void send(Packet<MType> packet) throws IOException {
        System.out.println("Sending Mtype: " + packet.getType() + " Size: " + packet.getSize());
        byte[] typeBytes = Utilities.intToByteArray(packet.getType().ordinal());
        this.out.write(typeBytes);
        byte[] bodySize = Utilities.intToByteArray(packet.getSize());
        this.out.write(bodySize);
        this.out.write(packet.getBody());
        this.out.flush();
    }

    public Packet<MType> receive() throws IOException {
        byte[] buf = new byte[4];
        this.in.read(buf, 0, 4);
        MType mtype = MType.values()[Utilities.byteArrayToInt(buf, 0)];
        this.in.read(buf, 0, 4);
        int bodySize = Utilities.byteArrayToInt(buf, 0);
        buf = new byte[bodySize];
        this.in.read(buf, 0, bodySize);
        return new Packet<>(mtype, buf);
    }

    public boolean isConnected() {
        return this.socket.isConnected();
    }

    public void close() throws IOException {
        this.socket.close();
    }


}
