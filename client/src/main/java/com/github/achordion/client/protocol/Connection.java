package com.github.achordion.client.protocol;

import java.io.*;
import java.net.Socket;

public class Connection {
    private OutputStream out;
    private InputStream in;

    public Connection(Socket socket) throws IOException {
        this.out = socket.getOutputStream();
        this.in = socket.getInputStream();
    }

    public void send(Packet<Mtype> packet) throws IOException {
        byte[] typeBytes = Utilities.intToByteArray(packet.getType().ordinal());
        this.out.write(typeBytes);
        byte[] bodySize = Utilities.intToByteArray(packet.getSize());
        this.out.write(bodySize);
        this.out.write(packet.getBody());
        this.out.flush();
    }

    public Packet<Mtype> receive() throws IOException {
        byte[] buf = new byte[4];
        this.in.read(buf, 0, 4);
        Mtype mtype = Mtype.values()[Utilities.byteArrayToInt(buf, 0)];
        this.in.read(buf, 0, 4);
        int bodySize = Utilities.byteArrayToInt(buf, 0);
        buf = new byte[bodySize];
        this.in.read(buf, 0, bodySize);
        return new Packet<>(mtype, buf);
    }

}
