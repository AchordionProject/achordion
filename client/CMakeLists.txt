cmake_minimum_required(VERSION 3.30)


project(achordion_client)

include(FetchContent)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

find_program(CCACHE_FOUND ccache)
if(CCACHE_FOUND)
    set_property(GLOBAL PROPERTY RULE_LAUNCH_COMPILE ccache)
endif()

set(wxBUILD_SHARED OFF)
FetchContent_Declare(
    wxWidgets
    GIT_REPOSITORY https://github.com/wxWidgets/wxWidgets.git
    GIT_PROGRESS true
    GIT_TAG v3.2.6
    GIT_SHALLOW ON
    GIT_DEPTH 1
)

FetchContent_Declare(
    portaudio
    GIT_REPOSITORY https://github.com/PortAudio/portaudio.git
    GIT_PROGRESS true
    GIT_TAG v19.7.0
    GIT_SHALLOW ON
    GIT_DEPTH 1
)

FetchContent_Declare(
    asio
    GIT_REPOSITORY https://github.com/chriskohlhoff/asio.git
    GIT_PROGRESS true
    GIT_TAG asio-1-31-0
    GIT_SHALLOW ON
    GIT_DEPTH 1
)

message(STATUS "Fetching wxWidgets from GitHub...")
FetchContent_MakeAvailable(wxWidgets)

message(STATUS "Fetching Port Audio from Github...")
FetchContent_MakeAvailable(portaudio)

message(STATUS "Fetching Asio from Github...")
FetchContent_MakeAvailable(asio)


set(SRCS src/main.cpp)

add_executable(achordion_client ${SRCS})

target_include_directories(achordion_client PRIVATE ${asio_SOURCE_DIR}/asio/include)
target_include_directories(achordion_client PRIVATE ${portaudio_SOURCE_DIR}/include)
target_link_libraries(achordion_client PRIVATE wxcore wxnet)
target_link_libraries(achordion_client PRIVATE portaudio)
