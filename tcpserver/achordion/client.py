import asyncio
import io
import enum
from dataclasses import dataclass
from typing import Callable, Mapping, Union
from achordion.chord import Chord, ChordType
from achordion.note import Note
from achordion.detect import run
from achordion.logger import achord_logger
from achordion.packing import pack_notes_into_bytes

PacketBytes = Union[bytes, bytearray]
SIGNATURE = 0xACC07D

class MessageType(enum.Enum):
    SERVERPING = 0
    CHORD = 1
    MALFORMED_MESSAGE = 2

    @staticmethod
    def chord_recognition(body: PacketBytes) -> "Packet":
        file = io.BytesIO(body)
        notes = run(file)
        body_to_send = bytearray()
        # Retrieve recognized chord
        body_to_send += Chord(Note.C, ChordType.MINOR).to_byte()
        body_to_send += len(notes).to_bytes(4, "big")
        notes_as_bytes = pack_notes_into_bytes(notes)
        body_to_send += notes_as_bytes
        return Packet(MessageType.CHORD, body_to_send)

MESSAGE_ACTIONS: Mapping[MessageType, Callable] = {
    MessageType.CHORD: MessageType.chord_recognition
}


@dataclass(frozen=True)
class Packet:
    mtype: MessageType
    body: PacketBytes

    def __repr__(self) -> str:
        return f"Mtype: {self.mtype} Size: {len(self.body)} Body: {self.body[:20]}..."

ERROR_PACKET = Packet(MessageType.MALFORMED_MESSAGE, bytes())

class MalformedPacketException(Exception):
    def __init__(self, *args: object) -> None:
        super().__init__(*args)

class ClientInterface:
    def __init__(self, reader: asyncio.StreamReader, writer: asyncio.StreamWriter) -> None:
        self.reader = reader
        self.writer = writer
        
    async def send(self, packet: Packet):
        achord_logger.info(f"Sending a packet: {packet}")
        self.writer.write(SIGNATURE.to_bytes(3, "big"))
        self.writer.write(packet.mtype.value.to_bytes(1, "big"))
        mlen_bytes = len(packet.body).to_bytes(4, "big")
        self.writer.write(mlen_bytes)
        self.writer.write(packet.body)     
        await self.writer.drain()

    async def receive(self) -> Packet:
        signature = await self.reader.readexactly(3)
        signature = int.from_bytes(signature, byteorder="big")
        mtype_bytes = await self.reader.readexactly(1)
        message_len_bytes = await self.reader.readexactly(4)
        mtype = int.from_bytes(mtype_bytes, byteorder="big")
        mlen = int.from_bytes(message_len_bytes, byteorder="big")
        body = await self.reader.readexactly(mlen)
        packet = Packet(MessageType(mtype), body)
        if signature != SIGNATURE:
            raise MalformedPacketException("Packet is malformed")
        achord_logger.info(f"Received a packet: {packet}")
        return packet

