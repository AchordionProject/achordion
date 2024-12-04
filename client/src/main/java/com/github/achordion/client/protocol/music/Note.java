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

    @Override
    public String toString() {
        return switch (this) {
            case C -> "C";
            case C_SHARP -> "C#";
            case D -> "D";
            case D_SHARP -> "D#";
            case E -> "E";
            case F -> "F";
            case F_SHARP -> "F#";
            case G -> "G";
            case G_SHARP -> "G#";
            case A -> "A";
            case A_SHARP -> "A#";
            case B -> "B";
            default -> "";
        };
    }
}
