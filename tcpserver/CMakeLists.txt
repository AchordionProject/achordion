cmake_minimum_required(VERSION 3.30)

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

FetchContent_MakeAvailable(asio)

set(SOURCES src/main.cpp)

add_executable(achordion_server ${SOURCES})
target_include_directories(achordion_server PRIVATE ${asio_SOURCE_DIR}/asio/include)
