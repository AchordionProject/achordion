import asyncio
import io
import socket
import enum
from typing import Callable, Mapping
from achordion.detect import run

class MessageType(enum.Enum):
    SERVERPING = 0
    SERVERACCEPT = 1
    CHORD = 2

    @staticmethod
    def chord_recognition(body: bytes) -> "Packet":
        file = io.BytesIO(body)
        notes = run(file)
        body_to_send = bytearray()
        body_to_send += len(notes).to_bytes(4, "little")
        for note in notes:
            body_to_send += note.value.to_bytes(4, "little")
        return Packet(MessageType.CHORD, body_to_send)




MESSAGE_ACTIONS: Mapping[MessageType, Callable] = {
    MessageType.CHORD: MessageType.chord_recognition
}


class Packet:
    def __init__(self, mtype: MessageType, body: bytes) -> None:
        self.mtype = mtype
        self.body = body

class ClientInterface:
    def __init__(self, reader: asyncio.StreamReader, writer: asyncio.StreamWriter) -> None:
        self.reader = reader
        self.writer = writer
        
    async def send(self, packet: Packet):
        mtype_bytes = packet.mtype.value.to_bytes(4, "little")
        self.writer.write(mtype_bytes) 
        mlen_bytes = len(packet.body).to_bytes(4, "little")
        self.writer.write(mlen_bytes)
        self.writer.write(packet.body)     
        await self.writer.drain()

    async def receive(self) -> Packet:
        mtype_bytes = await self.reader.readexactly(4)
        message_len_bytes = await self.reader.readexactly(4)
        mtype = int.from_bytes(mtype_bytes, byteorder="little")
        mlen = int.from_bytes(message_len_bytes, byteorder="little")
        body = await self.reader.readexactly(mlen)
        return Packet(MessageType(mtype), body)

