package com.github.achordion.client.protocol;

public class Packet<T extends Enum<T>> {
    private T mtype;
    private byte[] body;

    public Packet(T mtype, byte[] body){
        this.mtype = mtype;
        this.body = body;
    }
    public int getSize() {
        return body.length;
    }
    public T getType(){
        return this.mtype;
    }
    public byte[] getBody(){
        return this.body;
    }
}