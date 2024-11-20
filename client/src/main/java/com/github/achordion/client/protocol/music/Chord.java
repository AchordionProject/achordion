package com.github.achordion.client.protocol.music;

public record Chord(Note bnote, ChordType ctype) {
    public static Chord fromBytes(byte bits){
        int bnote = (bits >> 4) & 0xF;
        int ctype = (bits >> 0) & 0xF;

        return new Chord(Note.getNoteFromInt(bnote), ChordType.getChordTypeFromInt(ctype));
    }

    @Override
    public String toString() {
        return bnote + " " + ctype;
    }
}
