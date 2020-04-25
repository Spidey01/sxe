# vim: set filetype=cmake tabstop=4 shiftwidth=4 expandtab :

project(quad
    VERSION 0.1
    LANGUAGES CXX)

add_executable(quad
    src/Quad.cpp
    src/QuadGame.cpp
    src/main.cpp)

set_property(TARGET quad PROPERTY CXX_STANDARD 17)

target_include_directories(quad PRIVATE SxE)
target_link_libraries(quad PRIVATE SxE)

#TODO: install share/Quad.dat -> $datadir/QuadGame/Quad.dat
#TODO: install QuadGame.cfg -> $sysconfdir/xdg/QuadGame.cfg

