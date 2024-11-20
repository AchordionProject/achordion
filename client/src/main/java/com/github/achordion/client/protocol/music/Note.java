package com.github.achordion.client.protocol.music;

public enum Note {
    ZERO,
    C,
    C_SHARP,
    D,
    D_SHARP,
    E,
    F,
    F_SHARP,
    G,
    G_SHARP,
    A,
    A_SHARP,
    B;

    public static Note getNoteFromInt(int num) {
        Note[] values = Note.values();
        if(num < 0 || num > values.length) {
           // Hanlder properly
           return null;
        }
        return values[num];
    }
}
