package com.github.achordion.client.protocol.core;

import java.util.Arrays;

public class Packet<T extends Enum<T>> {
    private T mtype;
    private byte[] body;

    public Packet(T mtype, byte[] body){
        this.mtype = mtype;
        this.body = body;
    }
    public int getSize() {
        return this.body.length;
    }
    public T getType(){
        return this.mtype;
    }
    public byte[] getBody(){
        return this.body;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "mtype=" + mtype +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}