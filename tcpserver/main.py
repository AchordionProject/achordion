#!/usr/bin/env python3

import asyncio

from achordion.client import ClientInterface, MESSAGE_ACTIONS
from achordion.logger import achord_logger, initiate_logger

HOST = "0.0.0.0"
PORT = 60000

async def handle_client(reader: asyncio.StreamReader, writer: asyncio.StreamWriter):
    client_sock = writer.get_extra_info("peername")
    client_interface = ClientInterface(reader, writer)
    achord_logger.info(f"Connection received: {client_sock}")
    try:
        while True:
            packet = await client_interface.receive()
            packet_to_send = MESSAGE_ACTIONS[packet.mtype](packet.body)
            await client_interface.send(packet_to_send)
    except Exception as e:
        achord_logger.info(f"Connection closed: {client_sock}; Reason: {e}")
    finally:
        writer.close()
        await writer.wait_closed()

async def main():
    initiate_logger()
    server = await asyncio.start_server(handle_client, HOST, PORT)
    print(f"Listening on {HOST}:{PORT}")
    await server.serve_forever()

asyncio.run(main())
