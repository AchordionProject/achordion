#!/usr/bin/env python3

from typing import List, Set, Tuple
import librosa
import numpy as np
import io
from achordion.note import Note
from achordion.detect import recognize_notes
from collections import Counter, OrderedDict

def filter_notes(notes: List[Note]) -> Set[Note]:
    counter = Counter(notes)
    return { note for note, count in counter.items() if count >= 2 }

if __name__ == "__main__":
    audio_file_path = "a7-chord.ogg"  # Replace with your .wav file path
    with open(audio_file_path, 'rb') as f:
        file_bytes = f.read()
    file_obj = io.BytesIO(file_bytes)

    samples, sr = librosa.load("a7-chord.wav")

    notes = recognize_notes(samples, sr)
    notes = list(map(lambda tuple: tuple[1], notes))
    notes = filter_notes(notes)

    for note in notes:
        print(f"Note: {note}")
    #for freq, note in notes:
    #    print(f"Frequency: {freq:.2f} Hz, Note: {note}")


