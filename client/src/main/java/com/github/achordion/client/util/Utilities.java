package com.github.achordion.client.util;

public class Utilities {
    public static final byte[] intToByteArray(int value, int numBytes) {
        byte[] bytes = new byte[numBytes];
        if(numBytes > 4) {
            return bytes;
        }
        for (int i = numBytes - 1; i >= 0; i--) {
            bytes[numBytes - i - 1] = (byte) ((value >> (i * 8)) & 0xFF);
        }
        return bytes;
    }

    public static int byteArrayToInt(byte[] buf, int off, int numBytes) {
        int result = 0;
        for (int i = 0; i < numBytes; i++) {
            result |= (buf[i + off] & 0xFF) << ((numBytes - i - 1) * 8);
        }
        return result;
    }
}
