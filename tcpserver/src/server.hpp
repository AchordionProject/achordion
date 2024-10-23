#pragma once
#include "protocol.h"

enum class CustomMsgTypes : uint32_t
{
    ServerPing = 0,
    ServerAccept,
    ChordRecognition,
    ChordReport,
};

class AchordionServer : public olc::net::server_interface<CustomMsgTypes>
{
public:
    AchordionServer(uint16_t nPort) : olc::net::server_interface<CustomMsgTypes>(nPort) {}

protected:
    virtual bool OnClientConnect(std::shared_ptr<olc::net::connection<CustomMsgTypes>> client) override;

    virtual void OnClientDisconnect(std::shared_ptr<olc::net::connection<CustomMsgTypes>> client) override;

    virtual void OnMessage(std::shared_ptr<olc::net::connection<CustomMsgTypes>> client, olc::net::message<CustomMsgTypes>& msg) override;
};
