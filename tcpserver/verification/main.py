#!/usr/bin/env python3

import socket
import enum
from typing import Optional
sock = socket.socket()
sock.connect(("localhost", 60000))
print("Connected to server!")

class MessageType(enum.Enum):
    ServerPing = 0
    ServerAccept = 1
    ChordRecognition = 2
    ChordReport = 3

class Packet:
    def __init__(self, mtype: MessageType, body: bytes) -> None:
        self.mtype = mtype
        self.body = body

    def __repr__(self) -> str:
        repr_str = "Type: {}\nBody: {}\n"
        return repr_str.format(self.mtype, str(self.body))

    def send(self, sock: socket.socket) -> None:
        mtype_bytes = self.mtype.value.to_bytes(4, "little")
        sock.send(mtype_bytes)
        mlen_bytes = len(self.body).to_bytes(4, "little")
        sock.send(mlen_bytes)
        sock.send(self.body)

    @staticmethod
    def receive_packet(sock: socket.socket) -> 'Packet':
        mtype_bytes = sock.recv(4)
        message_len_bytes = sock.recv(4)
        mtype = int.from_bytes(mtype_bytes, byteorder="little")
        mlen = int.from_bytes(message_len_bytes, byteorder="little") 
        body = sock.recv(mlen)
        return Packet(MessageType(mtype), body)

    

packet: Optional[Packet] = None
packet = Packet.receive_packet(sock)
print(packet) 
packet = Packet(MessageType.ChordRecognition, "Hello World!".encode("utf-8"))
packet.send(sock)

while(True):
    print(packet) 
    packet = Packet.receive_packet(sock)
