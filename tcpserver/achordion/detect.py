from typing import FrozenSet, List, Mapping, Optional, Tuple, Set
from collections import Counter
import io
import numpy as np
import librosa
from achordion.logger import achord_logger
from achordion.note import Note, Interval, calculate_halfsteps_between_notes
from achordion.chord import ChordType

def recognize_notes(samples: np.ndarray, sr: int | float) -> List[Tuple[float, Note]] :
    stft = np.abs(librosa.stft(samples))
    freqs = librosa.fft_frequencies(sr=sr)
    magnitudes = np.mean(stft, axis=1)
    N=10
    top_indices = np.argsort(magnitudes)[::-1][:N]
    top_freq = freqs[top_indices]

    notes: List[Tuple[float, Note]] = []

    for freq in top_freq:
        halfsteps = Note.freq_to_halfsteps(freq)
        rnote = Note.halfsteps_to_note(halfsteps)
        notes.append((freq, rnote))
    notes = sorted(notes, key=lambda x: x[0])
    achord_logger.info(f"Recognized notes: {notes}")
    return notes

def filter_notes(notes: List[Note]) -> List[Note]:
    counter = Counter(notes)
    filtered_notes = [ note for note, count in counter.items() if count >= 2 ]
    achord_logger.info(f"Filtered notes: {filtered_notes}")
    return filtered_notes


CHORD_TYPES_INTERVALS: Mapping[ChordType, FrozenSet] = {
    ChordType.MAJOR: frozenset({ Interval.UNISON, Interval.MAJ3, Interval.PERF5 }),
    ChordType.MINOR: frozenset({ Interval.UNISON, Interval.MIN3, Interval.PERF5 }),
    ChordType.MAJOR7: frozenset({ Interval.UNISON, Interval.MAJ3, Interval.PERF5, Interval.MIN7 }),
    ChordType.MINOR7: frozenset({ Interval.UNISON, Interval.MIN3, Interval.PERF5, Interval.MIN7 }),
}

def calculate_intervals(notes: List[Note]) -> Set[Interval]:
    first_note = notes[0]
    print(f"first note: {first_note}")
    intervals = set()
    for note in notes:
        interval = calculate_halfsteps_between_notes(first_note, note)
        print(f"Interval between {first_note} and {note} is {interval}")
        intervals.add(interval)
    return intervals


def get_chord(intervals: Set[Interval]) -> ChordType:
    for chord_type, required_intervals in CHORD_TYPES_INTERVALS.items():
        if intervals.issubset(required_intervals):
            return chord_type
    return ChordType.Undefined

def run(file_obj: io.BytesIO) -> List[Note]:
    samples, sr = librosa.load(file_obj)
    notes = recognize_notes(samples, sr)
    notes = list(map(lambda tuple: tuple[1], notes))
    notes = filter_notes(notes)
    return notes
