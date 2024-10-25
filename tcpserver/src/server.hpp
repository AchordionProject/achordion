#pragma once
#include "protocol.h"

enum class MessageType : uint32_t
{
    ServerPing = 0,
    ServerAccept,
    ChordRecognition,
    Silience,
};

class AchordionServer : public olc::net::server_interface<MessageType>
{
public:
    AchordionServer(uint16_t nPort) : olc::net::server_interface<MessageType>(nPort) {}

protected:
    virtual bool OnClientConnect(std::shared_ptr<olc::net::connection<MessageType>> client) override;

    virtual void OnClientDisconnect(std::shared_ptr<olc::net::connection<MessageType>> client) override;

    virtual void OnMessage(std::shared_ptr<olc::net::connection<MessageType>> client, olc::net::message<MessageType>& msg) override;
};
