package com.github.achordion.client.protocol;

public class Packet<T extends Enum<T>> {
    private T mtype;
    private char[] body;

    public Packet(T mtype, char[] body){
        this.mtype = mtype;
        this.body = body;
    }
    public int getSize() {
        return body.length;
    }
    public T getType(){
        return this.mtype;
    }
    public char[] getBody(){
        return this.body;
    }
}