#include <iostream>
#define ASIO_STANDALONE
#include <asio.hpp>

int main(void) {
    asio::io_context io_context;
    asio::steady_timer t(io_context, asio::chrono::seconds(5));
    t.wait();
    std::cout << "Hello World!" << std::endl;
    return 0;
}
