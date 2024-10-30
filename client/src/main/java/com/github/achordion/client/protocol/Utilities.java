package com.github.achordion.client.protocol;

public class Utilities {
    public static final char[] intToCharArray(int value) {
        return new char[] {
                (char)(value >>> 24),
                (char)(value >>> 16),
                (char)(value >>> 8),
                (char)value
        };
    }
}
