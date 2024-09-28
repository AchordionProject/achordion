cmake_minimum_required(VERSION 3.30)


project(achordion_client)

include(FetchContent)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

set(wxBUILD_SHARED OFF)
FetchContent_Declare(
    wxWidgets
    GIT_REPOSITORY https://github.com/wxWidgets/wxWidgets.git
    GIT_TAG v3.2.6
    GIT_SHALLOW ON
)

message(STATUS "Fetching wxWidgets from GitHub...")
FetchContent_MakeAvailable(wxWidgets)

set(SRCS src/main.cpp)

add_executable(achordion_client ${SRCS})

target_link_libraries(achordion_client PRIVATE wxcore wxnet)
