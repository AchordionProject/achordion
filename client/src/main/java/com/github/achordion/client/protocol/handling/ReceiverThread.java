package com.github.achordion.client.protocol.handling;

import com.github.achordion.client.protocol.MainController;
import com.github.achordion.client.protocol.core.Connection;
import com.github.achordion.client.protocol.core.MType;
import com.github.achordion.client.protocol.core.Packet;

import java.util.concurrent.atomic.AtomicBoolean;

public class ReceiverThread extends Thread{

    private MainController controller;
    private AtomicBoolean isRunning;

    public ReceiverThread(MainController controller) {
        this.controller = controller;
        this.isRunning = new AtomicBoolean(true);
    }



    @Override
    public void run() {
        Connection connection = controller.getConnection();
        if (connection == null) {
            return;
        }
        while(connection.isConnected() && this.isRunning.get()) {
            try {
                System.out.println("Waiting for packet");
                Packet<MType> packet = connection.receive();
                System.out.println("Packet received " + packet);
                this.controller.handle(packet);
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
    }

    public void stopReceiverThread() {
        this.isRunning.set(false);
    }
}
