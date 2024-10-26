#include <iostream>
#include "server.hpp"
#include "protocol.h"

bool AchordionServer::OnClientConnect(std::shared_ptr<olc::net::connection<MessageType>> client)
{
    olc::net::message<MessageType> msg;
    msg.header.id = MessageType::ServerAccept;
    client->Send(msg);
    return true;
}

void AchordionServer::OnClientDisconnect(std::shared_ptr<olc::net::connection<MessageType>> client)
{
    std::cout << "Removing client [" << client->GetID() << "]\n";
}

void AchordionServer::OnMessage(std::shared_ptr<olc::net::connection<MessageType>> client, olc::net::message<MessageType>& msg)
{
    switch (msg.header.id)
        {
        case MessageType::ServerPing:
            {
                std::cout << "[" << client->GetID() << "]: Server Ping\n";

                // Simply bounce message back to client
                client->Send(msg);
            }
            break;

        case MessageType::ChordRecognition:
            {
                // call fft
                // recongize the chord
                olc::net::message<MessageType> response;
                response.header.id = MessageType::ChordRecognition;
                // push recognized chord onto response message
                response << "Recognized chord";
                client->Send(response);
            }
            break;
        }
}
