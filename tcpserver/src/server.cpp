#include "server.hpp"
#include "protocol.h"

bool AchordionServer::OnClientConnect(std::shared_ptr<olc::net::connection<CustomMsgTypes>> client)
{
    olc::net::message<CustomMsgTypes> msg;
    msg.header.id = CustomMsgTypes::ServerAccept;
    client->Send(msg);
    return true;
}

void AchordionServer::OnClientDisconnect(std::shared_ptr<olc::net::connection<CustomMsgTypes>> client)
{
    std::cout << "Removing client [" << client->GetID() << "]\n";
}

void AchordionServer::OnMessage(std::shared_ptr<olc::net::connection<CustomMsgTypes>> client, olc::net::message<CustomMsgTypes>& msg)
{
    switch (msg.header.id)
        {
        case CustomMsgTypes::ServerPing:
            {
                std::cout << "[" << client->GetID() << "]: Server Ping\n";

                // Simply bounce message back to client
                client->Send(msg);
            }
            break;


        }
}
