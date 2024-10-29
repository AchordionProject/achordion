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
                std::vector<uint8_t> bytes = msg.body;
                this->recognizer.store_fft(bytes);
                std::vector<int> peaks = this->recognizer.detect_peaks();
                std::vector<Note> notes = Note::get_notes(peaks);
                for(size_t i = 0; i < peaks.size(); i++) {
                    std::cout << peaks[i] << " : " << notes[i].to_string()  << std::endl;
                }
                std::cout << "------------------------------------------" << std::endl;
                olc::net::message<MessageType> response;
                response.header.id = MessageType::ChordRecognition;
                // push recognized chord onto response message
                response << "Recognized chord";
                client->Send(response);
            }
            break;
        }
}
