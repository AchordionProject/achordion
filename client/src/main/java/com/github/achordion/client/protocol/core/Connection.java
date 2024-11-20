package com.github.achordion.client.protocol.core;

import com.github.achordion.client.util.Utilities;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Connection {
    private static int SIGNATURE = 0xACC07D;
    private Socket socket;
    private OutputStream out;
    private CheckedInputStream in;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = socket.getOutputStream();
        this.in = new CheckedInputStream(socket.getInputStream());
    }

    public void send(Packet<MType> packet) throws IOException {
        System.out.println("Sending Mtype: " + packet.getType() + " Size: " + packet.getSize());
        byte[] sigBytes = Utilities.intToByteArray(SIGNATURE, 3);
        this.out.write(sigBytes);
        byte[] typeBytes = Utilities.intToByteArray(packet.getType().ordinal(), 1);
        this.out.write(typeBytes);
        byte[] bodySize = Utilities.intToByteArray(packet.getSize(), 4);
        this.out.write(bodySize);
        this.out.write(packet.getBody());
        this.out.flush();
    }

    public Packet<MType> receive() throws IOException, MalformedPacketException {
        byte[] buf = new byte[4];
        this.in.read(buf, 1, 3);
        int signature = Utilities.byteArrayToInt(buf, 0, 4);
        Arrays.fill(buf, (byte) 0);
        this.in.read(buf, 3, 1);
        MType mtype = MType.values()[Utilities.byteArrayToInt(buf, 0, 4)];
        this.in.read(buf, 0, 4);
        int bodySize = Utilities.byteArrayToInt(buf, 0, 4);
        buf = new byte[bodySize];
        this.in.read(buf, 0, bodySize);
        if(signature != SIGNATURE) {
            throw new MalformedPacketException("Invalid signature");
        }
        return new Packet<>(mtype, buf);
    }


    public boolean isConnected() {
        return this.socket.isConnected();
    }

    public void close() throws IOException {
        this.socket.close();
    }


}
