package com.github.achordion.client.protocol.music;

public enum ChordType {
    MAJOR, MINOR, MAJOR7, MINOR7;

    public static ChordType getChordTypeFromInt(int num) {
        ChordType[] values = ChordType.values();
        if(num < 0 || num > values.length) {
            // Hanlder properly
            return null;
        }
        return values[num];
    }
}
