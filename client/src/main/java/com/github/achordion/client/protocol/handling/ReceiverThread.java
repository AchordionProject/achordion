package com.github.achordion.client.protocol.handling;

import com.github.achordion.client.protocol.MainController;
import com.github.achordion.client.protocol.MainHandler;
import com.github.achordion.client.protocol.core.*;
import com.github.achordion.client.protocol.handling.events.DisconnectEvent;

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
                System.out.println(packet.toString());
                this.handler.handle(packet);
            } catch(SocketClosedException e) {
                System.out.println(e.getMessage());
                DisconnectEvent event = new DisconnectEvent(connection);
                this.handler.sendDisconnectEvent(event);
                this.stopReceiverThread();
            } catch (IOException e) {
                System.out.println("ERROR while receiving packet: " + e.getMessage());
            } catch (MalformedPacketException e) {
                System.out.println("ERROR packet is malformed: " + e.getMessage());
            }
        }
    }

    public void stopReceiverThread() {
        this.isRunning.set(false);
    }
}
