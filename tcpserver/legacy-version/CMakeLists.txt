cmake_minimum_required(VERSION 3.29)

project (achordion_server)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

find_program(CCACHE_FOUND ccache)
if(CCACHE_FOUND)
    set_property(GLOBAL PROPERTY RULE_LAUNCH_COMPILE ccache)
endif()

include(FetchContent)

FetchContent_Declare(
  asio
  GIT_REPOSITORY https://github.com/chriskohlhoff/asio.git
  GIT_PROGRESS true
  GIT_TAG asio-1-31-0
  GIT_SHALLOW ON
  GIT_DEPTH 1
)

FetchContent_Declare(
  kissfft
  GIT_REPOSITORY https://github.com/mborgerding/kissfft.git
  GIT_PROGRESS true
  GIT_TAG 131.1.0
  GIT_SHALLOW ON
  GIT_DEPTH 1
)

set(PROTOCOL_SOURCE_PATH ${CMAKE_SOURCE_DIR}/protocol)

FetchContent_MakeAvailable(asio)
set(KISSFFT_STATIC ON CACHE BOOL "Compile static Kiss FFT")
set(KISSFFT_TOOLS OFF CACHE BOOL "Disable Kiss FFT tools")
set(KISSFFT_TEST OFF CACHE BOOL "Disable Kiss FFT tests")
set(KISSFFT_DATATYPE "int16_t" CACHE STRING "Set datatype for Kiss FFT")
FetchContent_MakeAvailable(kissfft)


set(SOURCES src/main.cpp src/server.cpp src/recognition.cpp src/note.cpp)

add_executable(achordion_server ${SOURCES})
target_link_libraries(achordion_server PRIVATE kissfft)
target_include_directories(achordion_server PRIVATE ${asio_SOURCE_DIR}/asio/include)
target_include_directories(achordion_server PRIVATE ${PROTOCOL_SOURCE_PATH})
