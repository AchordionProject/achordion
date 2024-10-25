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


        }
}
