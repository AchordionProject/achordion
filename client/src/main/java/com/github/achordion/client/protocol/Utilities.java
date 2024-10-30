package com.github.achordion.client.protocol;

public class Utilities {
    public static final byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value << 24),
                (byte)(value << 16),
                (byte) (value << 8),
                (byte)(value << 0)
        };
    }

    public static int byteArrayToInt(byte[] buf, int off) {
        return ((buf[0 + off] << 24) | (buf[1 + off] << 16) | (buf[2 + off] << 8) | (buf[3 + off]));
    }
}
