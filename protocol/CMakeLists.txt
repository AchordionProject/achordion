cmake_minimum_required(VERSION 3.30)


project(achordion_protocol)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

set(SRCS src/message.cpp)

add_library(achordion_protocol STATIC ${SRCS})

target_include_directories(achordion_protocol PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})
