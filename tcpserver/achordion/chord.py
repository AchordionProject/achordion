import enum
from achordion.note import Note
from dataclasses import dataclass

class ChordType(enum.Enum):
    Undefined = 0
    MAJOR = 1
    MINOR = 2
    MAJOR7 = 3
    MINOR7 = 4

@dataclass(frozen=True)
class Chord:
    bnote: Note
    ctype: ChordType

    def to_byte(self) -> bytes:
        value = ((self.bnote.value & 0xF) << 4) | ((self.ctype.value & 0xF))
        return value.to_bytes(1, "big")
