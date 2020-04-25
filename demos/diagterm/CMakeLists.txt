# vim: set filetype=cmake tabstop=4 shiftwidth=4 expandtab :

project(diagterm
    VERSION 0.1
    LANGUAGES CXX)

add_executable(diagterm
    src/GameStub.cpp
    src/main.cpp)

target_include_directories(diagterm PRIVATE SxE)
target_link_libraries(diagterm PRIVATE SxE)

