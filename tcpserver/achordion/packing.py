from achordion.note import Note
from typing import List 

def pack_notes_into_bytes(notes: List[Note]) -> bytes:
    note_bytes = 0
    for i, note in enumerate(notes):
        note_bytes |= ((note.value & 0xF) << (i * 4))
    half_length = len(notes) // 2
    num_bytes = half_length if len(notes) % 2 == 0 else half_length + 1
    return note_bytes.to_bytes(num_bytes, byteorder="big")
