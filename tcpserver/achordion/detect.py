from typing import List, Tuple, Set
from collections import Counter
import io
import numpy as np
import librosa

from achordion.note import Note

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
    return sorted(notes, key=lambda x: x[0])

def filter_notes(notes: List[Note]) -> Set[Note]:
    counter = Counter(notes)
    return { note for note, count in counter.items() if count >= 2 }

def run(file_obj: io.BytesIO) -> Set[Note]:
    samples, sr = librosa.load(file_obj)
    notes = recognize_notes(samples, sr)
    notes = list(map(lambda tuple: tuple[1], notes))
    notes = filter_notes(notes)
    return notes
