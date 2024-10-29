from typing import List, Tuple
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

    return notes

