#pragma once

#include <vector>
#include <ostream>
#include <cstring>

namespace proto
{
    namespace net
    {
        template <typename T>
        struct message_header
        {
            T type{};
            size_t size = 0;
        };

        template <typename T>
        struct message
        {
            message_header<T> header;
            std::vector<std::byte> body;

            size_t size() const
            {
                return sizeof(message_header<T>) + body.size();
            }

            template <typename DataType>
            friend message<T>& operator << (message<T>& msg, const DataType& data)
            {
                static_assert(std::is_standard_layout<DataType>::value, "Data is too complex to be pushed into vector");
                size_t i = msg.body.size();
                msg.body.resize(i + sizeof(DataType));
                std::memcpy(msg.body.data() + i, &data, sizeof(DataType));
                msg.header.size = msg.body.size();
                return msg;
            }


            template <typename DataType>
            friend message<T>& operator >> (message<T>& msg, DataType& data)
            {
                static_assert(std::is_standard_layout<DataType>::value, "Data is too complex to be pushed into vector");
                size_t i = msg.body.size() - sizeof(DataType);
                std::memcpy(&data, msg.body.data() + i, sizeof(DataType));
                msg.body.resize(i);
                msg.header.size = i;
                return msg;
            }
        };
    }
}
