import enum
from achordion.note import Note
from dataclasses import dataclass

class ChordType(enum.Enum):
    MAJOR = 0
    MINOR = 1
    MAJOR7 = 2
    MINOR7 = 3

@dataclass(frozen=True)
class Chord:
    bnote: Note
    ctype: ChordType

    def to_byte(self) -> bytes:
        value = ((self.bnote.value & 0xF) << 4) | ((self.ctype.value & 0xF))
        return value.to_bytes(1, "big")
