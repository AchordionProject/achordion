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
        body_to_send += len(notes).to_bytes(4, "big")
        for note in notes:
            body_to_send += note.value.to_bytes(4, "big")
        return Packet(MessageType.CHORD, body_to_send)




MESSAGE_ACTIONS: Mapping[MessageType, Callable] = {
    MessageType.CHORD: MessageType.chord_recognition
}


class Packet:
    def __init__(self, mtype: MessageType, body: bytes) -> None:
        self.mtype = mtype
        self.body = body

    def __repr__(self) -> str:
        return f"Mtype: {self.mtype} Size: {len(self.body)} Body: {self.body[:20]}..."

class ClientInterface:
    def __init__(self, reader: asyncio.StreamReader, writer: asyncio.StreamWriter) -> None:
        self.reader = reader
        self.writer = writer
        
    async def send(self, packet: Packet):
        mtype_bytes = packet.mtype.value.to_bytes(4, "big")
        self.writer.write(mtype_bytes) 
        mlen_bytes = len(packet.body).to_bytes(4, "big")
        self.writer.write(mlen_bytes)
        self.writer.write(packet.body)     
        await self.writer.drain()

    async def receive(self) -> Packet:
        mtype_bytes = await self.reader.readexactly(4)
        print("Received mtype!")
        message_len_bytes = await self.reader.readexactly(4)
        print("Received mlen!")
        mtype = int.from_bytes(mtype_bytes, byteorder="big")
        mlen = int.from_bytes(message_len_bytes, byteorder="big")
        print(f"mtype is {mtype}")
        print(f"mlen is {mlen}")
        body = await self.reader.readexactly(mlen)
        print("Received body!")
        return Packet(MessageType(mtype), body)

