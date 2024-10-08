cmake_minimum_required(VERSION 3.30)

project(achordion_protocol)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

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

add_library(achordion_protocol INTERFACE)
target_include_directories(achordion_protocol INTERFACE src/)

target_include_directories(achordion_protocol INTERFACE ${asio_SOURCE_DIR}/asio/include)
