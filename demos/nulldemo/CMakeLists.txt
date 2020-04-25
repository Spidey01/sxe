# vim: set filetype=cmake tabstop=4 shiftwidth=4 expandtab :

project(nulldemo
    VERSION 0.1
    LANGUAGES CXX)

add_executable(nulldemo
    src/main.cpp
    src/NullDemo.cpp)

target_include_directories(nulldemo PRIVATE SxE)
target_link_libraries(nulldemo PRIVATE SxE)

