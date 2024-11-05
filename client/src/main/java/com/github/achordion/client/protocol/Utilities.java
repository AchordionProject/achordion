package com.github.achordion.client.protocol;

public class Utilities {
    public static final byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)((value >> 24) & 0xFF),
                (byte)((value >> 16) & 0xFF),
                (byte)((value >> 8) & 0xFF),
                (byte)((value >> 0) & 0xFF)
        };
    }

    public static int byteArrayToInt(byte[] buf, int off) {
        return (((buf[0 + off] & 0xFF) << 24) |
                ((buf[1 + off] & 0xFF) << 16) |
                ((buf[2 + off] & 0xFF) << 8)  |
                ((buf[3 + off] & 0xFF) << 0));
    }
}
