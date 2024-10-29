import enum
import numpy as np

A4_FREQ = 440.0

class Note(enum.Enum):
    C = 0
    C_SHARP = 1
    D = 2
    D_SHARP = 3
    E = 4
    F = 5
    F_SHARP = 6
    G = 7
    G_SHARP = 8
    A = 9
    A_SHARP = 10
    B = 11

    @staticmethod
    def freq_to_halfsteps(freq: float) -> int:
        return int(round(69 + 12 * np.log2(freq / A4_FREQ)))

    @staticmethod
    def halfsteps_to_note(halfsteps: int) -> "Note":
        return Note(halfsteps % 12)
