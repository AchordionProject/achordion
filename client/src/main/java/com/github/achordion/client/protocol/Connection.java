package com.github.achordion.client.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection<T extends Enum<T>> {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public void send(Packet<T> packet) {
        char[] typeBytes = Utilities.intToCharArray(packet.getType().ordinal());
        this.out.print(typeBytes);
        char[] bodySize = Utilities.intToCharArray(packet.getSize());
        this.out.print(bodySize);
        this.out.print(packet.getBody());
    }

    public void receive(){

    }

    public void setup() throws IOException {
        this.out = new PrintWriter(this.socket.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

}
