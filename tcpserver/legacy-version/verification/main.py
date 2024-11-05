#!/usr/bin/env python3

import socket
import wave
import enum
import numpy as np
from typing import Optional, Tuple

from numpy._typing import NDArray


def read_wavfile(filename: str) -> Tuple[NDArray, int] :
    with wave.open(filename, 'rb') as wav_file:
        n_channels = wav_file.getnchannels()
        frame_rate = wav_file.getframerate()
        n_frames = wav_file.getnframes()
        frames = wav_file.readframes(n_frames)
        audio_data = np.frombuffer(frames, dtype=np.uint16)  # Assuming 16-bit PCM
        audio_data = audio_data.reshape(-1, n_channels)
        audio_data = audio_data[:, 0]
        return audio_data, frame_rate

def get_next_samples(audio_data: NDArray, n_samples: int) -> Tuple[NDArray, NDArray]:
    print(audio_data.size)
    samples = audio_data[:n_samples]
    audio_data = audio_data[n_samples:]
    return audio_data, samples

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

def test():
    audio_data, frame_rate = read_wavfile("c-major.wav")
    print("Frame_rate: ", frame_rate)
    print("Data: ", audio_data)



def main():
    sock = socket.socket()
    sock.connect(("localhost", 60000))
    print("Connected to server!")
    packet: Optional[Packet] = None
    packet = Packet.receive_packet(sock)
    print(packet) 

    audio_data, frame_rate = read_wavfile("c-major.wav")
    audio_data, samples = get_next_samples(audio_data, 44100)
    sent = False

    while(True):
        if samples.size == 0:
            continue
        packet = Packet(MessageType.ChordRecognition, samples.tobytes())
        packet.send(sock)
        packet = Packet.receive_packet(sock)
        print(packet)
        audio_data, samples = get_next_samples(audio_data, 44100)

if __name__ == "__main__":
    main()
