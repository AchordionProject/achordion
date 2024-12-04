package com.github.achordion.client.protocol;

import com.github.achordion.client.protocol.core.Connection;
import com.github.achordion.client.protocol.handling.ReceiverThread;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class MainController {
    private static final MainController instance = new MainController();

    private Connection connection;
    private ReceiverThread receiverThread;

    private MainController() {
    }

    public static MainController getInstance() {
        return instance;
    }

    public void connect(String address, int port) throws IOException {
        InetAddress addr = InetAddress.getByName(address);
        Socket socket = new Socket(addr, port);
        this.disconnect();
        this.connection = new Connection(socket);
        this.receiverThread = new ReceiverThread();
        this.receiverThread.start();
    }

    public void disconnect() throws IOException {
        if(this.receiverThread == null || this.connection == null) { return; }
        this.receiverThread.stopReceiverThread();
        this.connection.close();
        this.receiverThread = null;
        this.connection = null;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return connection != null && connection.isConnected();
    }

}
