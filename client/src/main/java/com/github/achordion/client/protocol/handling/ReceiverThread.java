package com.github.achordion.client.protocol.handling;

import com.github.achordion.client.protocol.MainController;
import com.github.achordion.client.protocol.MainHandler;
import com.github.achordion.client.protocol.core.Connection;
import com.github.achordion.client.protocol.core.MType;
import com.github.achordion.client.protocol.core.Packet;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReceiverThread extends Thread{

    private MainController controller;
    private MainHandler handler;
    private AtomicBoolean isRunning;

    public ReceiverThread() {
        this.handler = MainHandler.getInstance();
        this.controller = MainController.getInstance();
        this.isRunning = new AtomicBoolean(true);
    }


    @Override
    public void run() {
        Connection connection = this.controller.getConnection();
        while(connection.isConnected() && this.isRunning.get()) {
            try {
                Packet<MType> packet = connection.receive();
                System.out.println("Calling handler here!");
                this.handler.handle(packet);
            } catch (IOException e) {
                System.out.println("ERROR while receiving packet: " + e.getMessage());
            }
        }
    }

    public void stopReceiverThread() {
        this.isRunning.set(false);
    }
}
