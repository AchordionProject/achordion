import enum
import numpy as np

A4_FREQ = 440.0

class Note(enum.Enum):
    NO_NOTE = 0
    C = 1
    C_SHARP = 2
    D = 3
    D_SHARP = 4
    E = 5
    F = 6
    F_SHARP = 7
    G = 8
    G_SHARP = 9
    A = 10
    A_SHARP = 11
    B = 12

    @staticmethod
    def freq_to_halfsteps(freq: float) -> int:
        return int(round(69 + 12 * np.log2(freq / A4_FREQ))) + 1

    @staticmethod
    def halfsteps_to_note(halfsteps: int) -> "Note":
        return Note(halfsteps % 12)
    
class Interval(enum.Enum):
    UNISON = 0
    MIN2 = 1
    MAJ2 = 2
    MIN3 = 3
    MAJ3 = 4
    PERF4 = 5
    TRITONE = 6
    PERF5 = 7
    MIN6 = 8
    MAJ6 = 9
    MIN7 = 10
    MAJ7 = 11

def calculate_halfsteps_between_notes(note1: Note, note2: Note) -> Interval:
    return Interval((note2.value - note1.value) % 12)
