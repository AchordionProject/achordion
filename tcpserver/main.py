#!/usr/bin/env python3

import asyncio

import socket

from achordion.client import ClientInterface, MESSAGE_ACTIONS

HOST = "127.0.0.1"
PORT = 60000

async def handle_client(reader: asyncio.StreamReader, writer: asyncio.StreamWriter):
    client_interface = ClientInterface(reader, writer)
    while True:
        packet = await client_interface.receive()
        packet_to_send = MESSAGE_ACTIONS[packet.mtype](packet.body)
        await client_interface.send(packet)

async def main():
    server = await asyncio.start_server(handle_client, HOST, PORT)
    print(f"Listening on {HOST}:{PORT}")
    async with server:
        await server.serve_forever()

asyncio.run(main())
